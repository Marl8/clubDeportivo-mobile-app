package com.example.clubdeportivo.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R
import com.example.clubdeportivo.utils.*

class InscribirActividadActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inscribir_actividad)

        setupUI()
        btnSelectActivityOnClick()
    }

    private fun setupUI(){
        //Asigno el Título de la pantalla. Solo es visible al correr la app
        val title = findViewById<TextView>(R.id.title_socio)
        title.text = "Inscribir Actividad"

        // Boton para volver al menu principal
        val btnBack : ImageButton = findViewById(R.id.back)
        backRedirect(this, btnBack)

        // Funcionalidad del botón Exit
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)
    }

    private fun btnSelectActivityOnClick(){
        val button = findViewById<Button>(R.id.btnSelectActivity)
        button.setOnClickListener{
            val intent = Intent(this, PopupSeleccionarActividad::class.java)
            startActivity(intent)
        }
    }
}