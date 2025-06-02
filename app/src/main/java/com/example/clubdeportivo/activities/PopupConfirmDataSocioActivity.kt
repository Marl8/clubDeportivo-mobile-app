package com.example.clubdeportivo.activities

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import com.example.clubdeportivo.R
import com.example.clubdeportivo.controllers.CuotaController
import com.example.clubdeportivo.controllers.SocioController
import com.example.clubdeportivo.entities.Socio
import com.example.clubdeportivo.fragments.CheckPaymentDialogFragment
import com.example.clubdeportivo.repositories.CuotaRepository
import com.example.clubdeportivo.repositories.SocioRepository
import com.example.clubdeportivo.utils.ModalStyleUtils
import com.google.android.material.button.MaterialButton

class PopupConfirmDataSocioActivity : AppCompatActivity() {

    private lateinit var cuotaController: CuotaController
    private lateinit var socioController: SocioController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_popup_confirm_data_socio)

        window.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        ModalStyleUtils.setUI(this)

        cuotaController = CuotaController(CuotaRepository(this), SocioRepository(this))
        socioController = SocioController(SocioRepository(this))
        val btnConfirm: MaterialButton = findViewById(R.id.confirm)
        val btnCancel: MaterialButton = findViewById(R.id.cancel)
        val txtId: TextView = findViewById(R.id.idSocio)
        val txtName: TextView = findViewById(R.id.nombre_socio)
        val txtDni: TextView = findViewById(R.id.dni)
        val txtValue: TextView = findViewById(R.id.value)
        val txtPayMethod: TextView = findViewById(R.id.payMethod)
        val txtNumCuotas: TextView = findViewById(R.id.numCuotas)

        val nameSocio = intent.getStringExtra("nombreSocio")
        val lastName = intent.getStringExtra("apellidoSocio")
        val id = intent.getStringExtra("idSocio")
        val dni = intent.getStringExtra("dniSocio")
        val email = intent.getStringExtra("emailSocio")
        val state = intent.getStringExtra("stateSocio")
        val phone = intent.getStringExtra("dniPhone")
        val apto = intent.getStringExtra("aptoFisico")
        val valueCuota = intent.getStringExtra("valueCuota")
        val payMethod = intent.getStringExtra("payMethod")
        val numCuotas = intent.getStringExtra("numberCuotas")

        txtId.text = id
        txtName.text = "$nameSocio $lastName"
        txtDni.text = dni
        txtValue.text = valueCuota
        txtPayMethod.text = payMethod
        txtNumCuotas.text = numCuotas

        val numberCuotas: Int = when (numCuotas) {
            "1 Cuota".trim() -> 1
            "3 Cuotas".trim() -> 3
            "6 Cuotas".trim() -> 6
            else -> 1
        }
        btnConfirm.setOnClickListener {

            var dialogState = false
            if (payMethod != null && valueCuota != null && nameSocio != null && lastName != null
                && dni != null && email != null && phone != null) {
                cuotaController.payCuota(payMethod, numberCuotas, valueCuota.toDouble(), dni)

                dialogState = true
                val aptoFisico = apto.toBoolean()
                val stateString = intent.getStringExtra("stateSocio")
                val stateBoolean = stateString?.toBoolean() ?: false  // Convertimos el String a Boolean
                val newState = !stateBoolean
                val socio = Socio(id?.toInt(), newState, aptoFisico, nameSocio, lastName, dni, email, phone)

                // Rehabilitamos al socio en caso de ser necesario
                socioController.updateSocio(socio)
            }
            showCheckPaymentDialog(dialogState)
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun showCheckPaymentDialog(success: Boolean) {
        val dialog = CheckPaymentDialogFragment.newInstance(success)
        dialog.show(supportFragmentManager, "CheckPaymentDialog")
    }
}