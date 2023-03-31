package com.example.vehicle_information

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRegister = findViewById(R.id.btn_Register)

        btnRegister.setOnClickListener {

            Toast.makeText(this, "Bye!!!", Toast.LENGTH_SHORT).show()

            var intent = Intent (this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

}