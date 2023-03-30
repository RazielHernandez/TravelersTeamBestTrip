package centennialcollege.ca.besttrip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import centennialcollege.ca.besttrip.repo.network.TravelersService
import centennialcollege.ca.besttrip.viewModel.TravelersViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    //private lateinit var viewModel: TravelersViewModel

    companion object{
        const val TAG = "centennialcollege.ca.besttrip:MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*viewModel = ViewModelProvider(this)[TravelersViewModel::class.java]

        viewModel.getProductList()
        viewModel.users.observe(this) {
            Log.e("TAG","${it.users.size} users were founded")
        }

        val myButton = findViewById<Button>(R.id.route_search_button)
        myButton.setOnClickListener {
            Log.e("TAG","Testing")
            //var listOfUsers = TravelersService.retrofit.getAllUsers()
        }*/
        val db = Firebase.firestore
        db.collection("travelers_users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }


    }

}