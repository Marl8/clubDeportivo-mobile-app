package com.example.clubdeportivo.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        val socio: ImageButton = findViewById(R.id.btnSocio)

        socio.setOnClickListener{
            val intent = Intent(this, InscribirSocioActivity::class.java)
            startActivity(intent)
        }
    }
}