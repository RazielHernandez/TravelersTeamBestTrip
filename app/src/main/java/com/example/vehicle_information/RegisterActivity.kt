package com.example.vehicle_information

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var progressBar : ProgressBar
    private var db = Firebase.firestore

    private lateinit var editTextYear : EditText
    private lateinit var editTextMaker : EditText
    private lateinit var editTextModel : EditText
    private lateinit var editTextFuel : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        progressBar = findViewById(R.id.progressBar)
        editTextYear = findViewById(R.id.carYear)
        editTextMaker = findViewById(R.id.carMaker)
        editTextModel = findViewById(R.id.carModel)
        editTextFuel = findViewById(R.id.carFuel)

        var btnSave = findViewById<Button>(R.id.btnSave)

        progressBar.visibility = View.INVISIBLE

        btnSave.setOnClickListener {

            Thread.sleep(1000)

            val editMaker = editTextMaker.text.toString().trim()
            val editModel = editTextModel.text.toString().trim()
            val editYear = editTextYear.text.toString().trim()
            val editFuel = editTextFuel.text.toString().trim()

            val vehicle = hashMapOf(
                "maker" to editMaker,
                "model" to editModel,
                "year" to editYear,
                "fuel" to editFuel
            )

            // Save data in Firebase
            db.collection("TravelerVehicle").document("Vehicle").set(vehicle)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully Added!", Toast.LENGTH_SHORT).show()

                    editTextMaker.text.clear()
                    editTextModel.text.clear()
                    editTextYear.text.clear()
                    editTextFuel.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }


            progressBar.visibility = View.VISIBLE
            Thread.sleep(1000)

            var intent = Intent (this, Vehicleinformation::class.java)
            startActivity(intent)

        }

    }
}