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

        val btnSocio: ImageButton = findViewById(R.id.btnSocio)
        val btnNoSocio: ImageButton = findViewById(R.id.btnNoSocio)

        btnSocio.setOnClickListener{
            val intent = Intent(this, InscribirSocioActivity::class.java)
            startActivity(intent)
        }

        btnNoSocio.setOnClickListener{
            val intent = Intent(this, InscribirNoSocioActivity::class.java)
            startActivity(intent)
        }
    }
}