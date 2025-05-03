package com.example.clubdeportivo.activities

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R
import com.example.clubdeportivo.utils.setupLogoutButton

class InscribirNoSocioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inscribir_no_socio)

        //Botón para cerrar sesión -
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)
    }
}