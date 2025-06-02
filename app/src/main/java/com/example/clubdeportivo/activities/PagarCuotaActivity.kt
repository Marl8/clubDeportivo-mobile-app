package com.example.clubdeportivo.activities

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R
import com.example.clubdeportivo.controllers.SocioController
import com.example.clubdeportivo.entities.Socio
import com.example.clubdeportivo.repositories.SocioRepository
import com.example.clubdeportivo.utils.ClearFormUtils
import com.example.clubdeportivo.utils.UserSessionUtil
import com.example.clubdeportivo.utils.backRedirect
import com.example.clubdeportivo.utils.setupLogoutButton
import com.google.android.material.button.MaterialButton
import com.jakewharton.threetenabp.AndroidThreeTen

class PagarCuotaActivity : AppCompatActivity() {

    private lateinit var paymentMethodDropdown: AutoCompleteTextView
    private lateinit var installmentsDropdown: AutoCompleteTextView
    private lateinit var socioController: SocioController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagar_cuota)
        AndroidThreeTen.init(this)

        setupUI()
        setupDropdwons()

        socioController = SocioController(SocioRepository(this))
        val btnSend: MaterialButton = findViewById(R.id.send)
        val txtDni: EditText = findViewById(R.id.dniInput)
        val txtAmount: EditText = findViewById(R.id.amount)
        val formLayout: ViewGroup = findViewById(R.id.body)

        btnSend.setOnClickListener {
            val paymentMethod = paymentMethodDropdown.text.toString()
            val installment = installmentsDropdown.text.toString()
            val dni = txtDni.text.toString()
            val amountCuota = txtAmount.text.toString()

            if (paymentMethod.isEmpty() || installment.isEmpty() || dni.isEmpty()) {
                Toast.makeText(this, "Selecciona todas las opciones", Toast.LENGTH_SHORT).show()
            }else {
                val socio: Socio? = socioController.getSocio(dni)
                if (socio != null) {
                    val intent = Intent(this, PopupConfirmDataSocioActivity::class.java)
                    intent.putExtra("valueCuota", amountCuota)
                    intent.putExtra("payMethod", paymentMethod)
                    intent.putExtra("numberCuotas", installment)
                    intent.putExtra("idSocio", socio.idSocio.toString())
                    intent.putExtra("nombreSocio", socio.name)
                    intent.putExtra("apellidoSocio", socio.lastName)
                    intent.putExtra("dniSocio", socio.dni)
                    intent.putExtra("emailSocio", socio.email)
                    intent.putExtra("stateSocio", socio.stateSocio)
                    intent.putExtra("dniPhone", socio.phone)
                    intent.putExtra("aptoFisico", socio.aptoFisico)
                    ClearFormUtils.clearForm(formLayout)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Error el socio no existe", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupDropdwons(){
        // Listas dinámicas
        val paymentMethods = resources.getStringArray(R.array.payment_methods)
        val installments = resources.getStringArray(R.array.installments)
        val onlyOneInstallment = arrayOf("1 Cuota")

        // Seteo los adapters dinámicamente
        val paymentAdapter = ArrayAdapter(this, R.layout.component_list_item, paymentMethods)
        paymentMethodDropdown.setAdapter(paymentAdapter)

        val installmentsAdapter = ArrayAdapter(this, R.layout.component_list_item, installments)
        installmentsDropdown.setAdapter(installmentsAdapter)

        paymentMethodDropdown.setOnItemClickListener { _, _, position, _ ->
            val selectedMethod = paymentMethods[position]
            val selectedInstallments = if (selectedMethod.equals("Efectivo", ignoreCase = true)) {
                onlyOneInstallment
            } else {
                installments
            }
            installmentsDropdown.setAdapter(
                ArrayAdapter(this, R.layout.component_list_item, selectedInstallments)
            )
            installmentsDropdown.setText("") // Limpia selección anterior
        }
    }

    private fun setupUI(){

        // Asocio las vistas con findViewById
        paymentMethodDropdown = findViewById(R.id.paymentMethodDropdown)
        installmentsDropdown = findViewById(R.id.installmentsDropdown)

        // Personalizamos el header con el nombre del usuario
        val username = UserSessionUtil.getUserSession(this)
        val role = UserSessionUtil.getUserRole(this)
        val txtWelcome: TextView = findViewById(R.id.txtWelcome)
        txtWelcome.text = "Bienvenido! $username"

        // Boton para volver al menu principal
        val btnBack : ImageButton = findViewById(R.id.back)
        backRedirect(this,btnBack)

        //Asigno el Título de la pantalla. Solo es visible al correr la app
        val title = findViewById<TextView>(R.id.title_socio)
        title.text = "Pago de Cuota"

        // Funcionalidad del botón Exit
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)
    }
}
