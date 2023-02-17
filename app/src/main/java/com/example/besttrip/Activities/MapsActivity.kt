package com.example.besttrip.Activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.besttrip.BuildConfig.MAPS_API_KEY
import com.example.besttrip.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.besttrip.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import org.json.JSONObject

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isCompassEnabled = true
        mMap.setOnInfoWindowClickListener(this)
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.uiSettings.isZoomControlsEnabled = true

        val origin = LatLng(43.684345, -79.431292) //Toronto
        //val origin = LatLng(45.41117,-75.69812) //Ottawa Origin
        //val destination = LatLng(44.299999,-78.316666) //Peterborough Destination
        //val destination = LatLng(45.41117,-75.69812) //Ottawa Destination
        val destination = LatLng(43.595310,-79.640579) //Mississauga
        //val destination = LatLng(46.322536,-79.456360) //Ottawa
        mMap.addMarker(MarkerOptions().position(origin).title("Marker Origin"))
        mMap.addMarker(MarkerOptions().position(destination).title("Marker Destination"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 7.0f))

        val path: MutableList<List<LatLng>> = ArrayList()
        val urlDirections = "https://maps.googleapis.com/maps/api/directions/json?origin="+ origin.latitude.toString() + "," + origin.longitude.toString() +
                "&destination=" + destination.latitude.toString() + "," + destination.longitude.toString() + "&key=${MAPS_API_KEY}"
        val directionsRequest = object : StringRequest(Method.GET, urlDirections, Response.Listener<String> {
                response ->
            val jsonResponse = JSONObject(response)
            // Get routes
            val routes = jsonResponse.getJSONArray("routes")
            val legs = routes.getJSONObject(0).getJSONArray("legs")
            val steps = legs.getJSONObject(0).getJSONArray("steps")

            Toast.makeText(applicationContext,
                legs.getJSONObject(0).getJSONObject("distance").getString("text") +
                        " " + legs.getJSONObject(0).getJSONObject("duration").getString("text"),
                Toast.LENGTH_LONG).show()

            for (i in 0 until steps.length()) {
                val points = steps.getJSONObject(i).getJSONObject("polyline").getString("points")
                path.add(PolyUtil.decode(points))
            }
            for (i in 0 until path.size) {
                mMap.addPolyline(PolylineOptions().addAll(path[i]).color(Color.RED))
            }
        }, Response.ErrorListener {
                _ ->
        }){}
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(directionsRequest)
    }

    override fun onInfoWindowClick(p0: Marker) {
        TODO("Not yet implemented")
    }
}