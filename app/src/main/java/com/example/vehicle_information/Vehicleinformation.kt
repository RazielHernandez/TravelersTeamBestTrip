package com.example.vehicle_information

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Vehicleinformation : AppCompatActivity() {

    private lateinit var viewMaker: TextView
    private lateinit var viewModel: TextView
    private lateinit var viewYear: TextView
    private lateinit var viewFuel: TextView

    private lateinit var updateMaker: TextView
    private lateinit var updateModel: TextView
    private lateinit var updateYear: TextView
    private lateinit var updateFuel: TextView

    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    private lateinit var btnUpdateShow: Button

    private var db = Firebase.firestore

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_information)


        viewMaker = findViewById(R.id.txtMaker)
        viewModel = findViewById(R.id.txtModel)
        viewYear = findViewById(R.id.txtYear)
        viewFuel = findViewById(R.id.txtFuel)

        btnUpdate = findViewById(R.id.btnUpdate)

        // Get data from Firebase
        val ref = db.collection("TravelerVehicle").document("Vehicle")
        ref.get().addOnSuccessListener {
            if (it != null) {
                val maker = it.data?.get("maker")?.toString()
                val model = it.data?.get("model")?.toString()
                val year = it.data?.get("year")?.toString()
                val fuel = it.data?.get("fuel")?.toString()

                viewMaker.text = maker
                viewModel.text = model
                viewYear.text = year
                viewFuel.text = fuel

            }
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
            }

        // Create dialog view
        val dialogBinding = layoutInflater.inflate(R.layout.update_information, null)
        val myDialog = Dialog(this)
        myDialog.setContentView(dialogBinding)

        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Update button
        btnUpdate.setOnClickListener {
            // Show dialog view
            myDialog.show()

            // Dialog update button
            btnUpdateShow = dialogBinding.findViewById(R.id.btn_Update_CardView)
            btnUpdateShow.setOnClickListener {

                updateMaker = dialogBinding.findViewById(R.id.edit_Maker)
                val editMakerShow  = updateMaker.text.toString().trim()
                updateModel = dialogBinding.findViewById(R.id.edit_Model)
                val editModelShow  = updateModel.text.toString().trim()
                updateYear = dialogBinding.findViewById(R.id.edit_Year)
                val editYearShow  = updateYear.text.toString().trim()
                updateFuel = dialogBinding.findViewById(R.id.edit_Fuel)
                val editFuelShow  = updateFuel.text.toString().trim()


                val updateVehicle = mapOf(
                    "maker" to editMakerShow ,
                    "model" to editModelShow ,
                    "year" to editYearShow ,
                    "fuel" to editFuelShow
                )

                // Update data in Firebase
                db.collection("TravelerVehicle").document("Vehicle").update(updateVehicle)

                Toast.makeText(this, "Successfully Edited!", Toast.LENGTH_SHORT).show()

                var intent = Intent (this, Vehicleinformation::class.java)
                startActivity(intent)

            }
        }

        // Delete button
        btnDelete = findViewById(R.id.btnDelete)
        btnDelete.setOnClickListener {

            db.collection("TravelerVehicle").document("Vehicle").delete()
            Toast.makeText(this, "Successfully Deleted!", Toast.LENGTH_SHORT).show()

            Thread.sleep(1000)
            var intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}