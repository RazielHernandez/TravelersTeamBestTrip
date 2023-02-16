package centennialcollege.ca.besttrip.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import centennialcollege.ca.besttrip.R
import centennialcollege.ca.besttrip.utilities.FuelConsumptionProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class RouteActivity: AppCompatActivity(), OnMapReadyCallback {

    companion object{
        const val TAG = "centennialcollege.ca.besttrip.activities:RouteActivity"
    }

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        //fuelConsumptionReader().fuelConsumptionReader(context = this, R.raw.fuel_consumption_ratings_2023)
        val reader = FuelConsumptionProvider(context1 = this, id1 = R.raw.fuel_consumption_ratings_2023)
        val record = reader.fuelConsumptionSearch("2023","Acura","MDX SH-AWD Type S")

        Log.e(TAG, "Your car "+record.model+"/"+record.maker)
        Log.e(TAG, "Has a gas consumption: "+record.fuelOnCity+" - "+record.fuelOnHighway+" - "+record.fuelOnCombined)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}