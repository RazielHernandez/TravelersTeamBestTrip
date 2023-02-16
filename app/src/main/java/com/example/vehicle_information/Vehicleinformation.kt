package com.example.vehicle_information

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class Vehicleinformation : AppCompatActivity() {

    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_information)

        sharedPreferences = getSharedPreferences("Vehicle", MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        val year = sharedPreferences.getString("year","")
        val brand = sharedPreferences.getString("brand","")
        val model = sharedPreferences.getString("model","")
        val fuel = sharedPreferences.getString("fuel","")
        editor.apply()
        editor.commit()

        val textYear = findViewById<TextView>(R.id.txtYear)
        val textBrand = findViewById<TextView>(R.id.txtBrand)
        val textModel = findViewById<TextView>(R.id.txtModel)
        val textFuel = findViewById<TextView>(R.id.txtFuel)

        textYear.text = year
        textBrand.text = brand
        textModel.text = model
        textFuel.text = fuel

    }
}