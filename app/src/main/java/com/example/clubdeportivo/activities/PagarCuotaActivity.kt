package com.example.clubdeportivo.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R
import com.example.clubdeportivo.utils.setupLogoutButton
import com.google.android.material.button.MaterialButton

class PagarCuotaActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagar_cuota)

        val title = findViewById<TextView>(R.id.title_socio)
        title.text = "Pago de Cuota"
        //Botón para cerrar sesión -
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)

        val paymentMethodSpinner: Spinner = findViewById(R.id.paymentMethodSpinner)
        val installmentsSpinner: Spinner = findViewById(R.id.installmentsSpinner)

        // Datos para el Spinner de Forma de Pago
        val paymentAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.payment_methods,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        paymentMethodSpinner.adapter = paymentAdapter


        // Datos para el Spinner de Cantidad de Cuotas
        val installmentsAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.installments,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        installmentsSpinner.adapter = installmentsAdapter

        // Manejo del botón de Enviar
        val sendPaymentButton: MaterialButton = findViewById(R.id.send)
        sendPaymentButton.setOnClickListener{
            val paymentMethod = paymentMethodSpinner.selectedItem.toString()
            val installments = installmentsSpinner.selectedItem.toString()


            //Verifico que ambos spinners tienen una opción seleccionada
            if(paymentMethod == resources.getStringArray(R.array.payment_methods)[0] ||
                installments == resources.getStringArray(R.array.installments)[0]){
                Toast.makeText(this, "Por favor, seleccione correctamente ambos campos.",
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"Método de pago: $paymentMethod, Cuotas: $installments",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }




}