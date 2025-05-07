package com.example.clubdeportivo.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R
import com.example.clubdeportivo.utils.*

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        //Botón para cerrar sesión -
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)

        val btnSocio: ImageButton = findViewById(R.id.btnSocio)
        val btnNoSocio: ImageButton = findViewById(R.id.btnNoSocio)
        val btnCarnet: ImageButton = findViewById(R.id.btnCarnet)
        val btnCuota: ImageButton = findViewById(R.id.btnPagoCuota)
        val btnListaMorosos: ImageButton = findViewById(R.id.btnListaMorosos)
        val btnListaVencimientos: ImageButton = findViewById(R.id.btnListaVencimientos)

        btnSocio.setOnClickListener{
            val intent = Intent(this, InscribirSocioActivity::class.java)
            startActivity(intent)
        }

        btnNoSocio.setOnClickListener{
            val intent = Intent(this, InscribirNoSocioActivity::class.java)
            startActivity(intent)
        }

        btnCarnet.setOnClickListener {
            val intent = Intent(this, CarnetActivity::class.java)
            startActivity(intent)
        }

        btnCuota.setOnClickListener{
            val intent = Intent(this, PagarCuotaActivity::class.java)
            startActivity(intent)
        }

        btnListaMorosos.setOnClickListener {
            val intent = Intent(this, ListaMorososActivity::class.java)
            startActivity(intent)
        }

        btnListaVencimientos.setOnClickListener {
            val intent = Intent(this, ListaVencimientosActivity::class.java)
            startActivity(intent)
        }
    }
}