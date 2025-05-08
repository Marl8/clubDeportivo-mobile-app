package com.example.clubdeportivo.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R
import com.example.clubdeportivo.utils.setupLogoutButton

class PagarCuotaActivity : AppCompatActivity() {

    private lateinit var paymentMethodDropdown: AutoCompleteTextView
    private lateinit var installmentsDropdown: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagar_cuota)

        setupUI()
        setupDropdwons()
    }

    private fun setupDropdwons(){
        // Listas dinámicas
        val paymentMethods = resources.getStringArray(R.array.payment_methods)
        val installments = resources.getStringArray(R.array.installments)

        // Seteo los adapters dinámicamente
        val paymentAdapter = ArrayAdapter(this, R.layout.component_list_item, paymentMethods)
        paymentMethodDropdown.setAdapter(paymentAdapter)

        val installmentsAdapter = ArrayAdapter(this, R.layout.component_list_item, installments)
        installmentsDropdown.setAdapter(installmentsAdapter)
    }

    private fun setupUI(){

        // Asocio las vistas con findViewById
        paymentMethodDropdown = findViewById(R.id.paymentMethodDropdown)
        installmentsDropdown = findViewById(R.id.installmentsDropdown)

        // Boton para volver al menu principal
        val btnBack : ImageButton = findViewById(R.id.back)
        backRedirect(btnBack)

        //Asigno el Título de la pantalla. Solo es visible al correr la app
        val title = findViewById<TextView>(R.id.title_socio)
        title.text = "Pago de Cuota"

        // Funcionalidad del botón Exit
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)
    }

    private fun backRedirect(btnBack:ImageButton){
        btnBack.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)

            // Limpiar la pila de actividades
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(intent)
            finish() // cierra la actividad actual
        }
    }
}
