package com.example.vehicle_information

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Intents.Insert
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import java.util.EmptyStackException

class MainActivity : AppCompatActivity() {

    var editTextYear : EditText? = null
    var editTextBrand : EditText? = null
    var editTextModel : EditText? = null
    var editTextFuel : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextYear = findViewById(R.id.editYear)
        editTextBrand = findViewById(R.id.editBrand)
        editTextModel = findViewById(R.id.editModel)
        editTextFuel = findViewById(R.id.editFuel)

        var btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {

            var con = SQLite (this, "Information", null, 1)
            var database = con.writableDatabase

            var year = editTextYear?.text.toString()
            var brand = editTextBrand?.text.toString()
            var model = editTextModel?.text.toString()
            var fuel = editTextFuel?.text.toString()

            if (year.isNotEmpty() && brand.isNotEmpty()
                && model.isNotEmpty() && fuel.isNotEmpty()
            ) {

                var register = ContentValues()
                register.put("year", year)
                register.put("brand", brand)
                register.put("model", model)
                register.put("fuel", fuel)

                database.insert("Vehicle", null, register)

                editTextYear?.setText(" ")
                editTextBrand?.setText(" ")
                editTextModel?.setText(" ")
                editTextFuel?.setText(" ")

                Toast.makeText(this, "Successful Registration", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }

        }

    }

}