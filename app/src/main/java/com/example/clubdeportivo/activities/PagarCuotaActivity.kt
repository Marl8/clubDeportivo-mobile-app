package com.example.clubdeportivo.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R

class PagarCuotaActivity : AppCompatActivity() {

    private lateinit var paymentMethodDropdown: AutoCompleteTextView
    private lateinit var installmentsDropdown: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagar_cuota)

        // Asocio las vistas con findViewById
        paymentMethodDropdown = findViewById(R.id.paymentMethodDropdown)
        installmentsDropdown = findViewById(R.id.installmentsDropdown)

        // Listas dinámicas
        val paymentMethods = listOf("Efectivo", "Crédito")
        val installments = listOf("1 cuota", "3 cuotas", "6 cuotas")

        // Seteo los adapters dinámicamente
        val paymentAdapter = ArrayAdapter(this, R.layout.component_list_item, paymentMethods)
        paymentMethodDropdown.setAdapter(paymentAdapter)

        val installmentsAdapter = ArrayAdapter(this, R.layout.component_list_item, installments)
        installmentsDropdown.setAdapter(installmentsAdapter)
    }
}
