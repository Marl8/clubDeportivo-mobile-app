package com.example.clubdeportivo.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R
import com.example.clubdeportivo.databinding.ActivityPagarCuotaBinding
import com.example.clubdeportivo.utils.setupLogoutButton
import com.google.android.material.button.MaterialButton

class PagarCuotaActivity: AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var binding: ActivityPagarCuotaBinding
    private var installmentsEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPagarCuotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupDropdowns()
        setupPayButton()
        }

    private fun setupUI(){
        val title = findViewById<TextView>(R.id.title_socio)
        title.text = "Pago de Cuota"

        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)

    }

    private fun setupDropdowns(){
        val paymentMethods = resources.getStringArray(R.array.payment_methods)
        val installments = resources.getStringArray(R.array.installments)
        val paymentAdapter = ArrayAdapter(
            this,
            R.layout.component_list_item,
            paymentMethods
        )

        val installmentsAdapter = ArrayAdapter(
            this,
            R.layout.component_list_item,
            installments
        )

        with(binding.installmentsDropdown){
            setAdapter(installmentsAdapter)
            onItemClickListener = this@PagarCuotaActivity
        }

        with(binding.paymentMethodDropdown){
            setAdapter(paymentAdapter)
            onItemClickListener = this@PagarCuotaActivity
        }
    }

    private fun setupPayButton(){
        binding.send.setOnClickListener{
            val dni = binding.dniInput.text.toString()
            val selectedPaymentMethod = binding.paymentMethodDropdown.text.toString()
            val selectedInstallments = binding.installmentsDropdown.text.toString()
            val amount = binding.amount.text.toString()

            when {
                dni.isBlank() -> {
                    Toast.makeText(this, "Por favor, ingresá el DNI", Toast.LENGTH_SHORT).show()
                }
                selectedPaymentMethod.isBlank() -> {
                    Toast.makeText(this, "Por favor, elegí tu forma de pago", Toast.LENGTH_SHORT).show()
                }
                selectedPaymentMethod == "Tarjeta de Crédito" && selectedInstallments.isBlank() -> {
                    Toast.makeText(this, "Por favor, elegí la cantidad de cuotas", Toast.LENGTH_SHORT).show()
                }
                amount.isBlank() -> {
                    Toast.makeText(this, "Por favor, ingresá el valor de la cuota", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Pago realizado con éxito", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition(position).toString()
        Toast.makeText(this@PagarCuotaActivity, item, Toast.LENGTH_SHORT).show()

        when (parent?.id){
            R.id.paymentMethodDropdown -> {

                binding.paymentMethodDropdown.hint=""
                Log.d("PagarCuota", "Ya eligió metodo")
            }
            R.id.installmentsDropdown -> {
                binding.installmentsDropdown.hint=""
            }
        }
    }

}