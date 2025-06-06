package com.example.clubdeportivo.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R
import com.example.clubdeportivo.controllers.ActividadController
import com.example.clubdeportivo.controllers.NoSocioController
import com.example.clubdeportivo.entities.NoSocio
import com.example.clubdeportivo.fragments.CheckEnrollActividadDialogFragment
import com.example.clubdeportivo.repositories.ActividadRepository
import com.example.clubdeportivo.repositories.NoSocioRepository
import com.example.clubdeportivo.repositories.SocioRepository
import com.example.clubdeportivo.utils.*
import com.google.android.material.button.MaterialButton
import com.jakewharton.threetenabp.AndroidThreeTen

class PagarActividadDiariaActivity: AppCompatActivity() {

    private var optionSelect: String = ""
    private lateinit var actividadController: ActividadController
    private lateinit var noSocioController: NoSocioController

    /**
     * Registra un lanzador de resultados y utiliza un contrato que te permite iniciar
     * una nueva actividad y esperar un resultado como retorno el cual queda guardado
     * en la variable optionSelect.
     * */
    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            optionSelect = result.data?.getStringExtra("selected_activity") ?: ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.drawer_pagar_actividad_diaria)

        UserMenuUtils.setupDrawer(this)
        setupUI()
        btnSelectActivityOnClick()
        AndroidThreeTen.init(this)

        actividadController = ActividadController(
            ActividadRepository(this),
            SocioRepository(this), NoSocioRepository(this)
        )
        noSocioController = NoSocioController(NoSocioRepository(this))
        val txtDni: EditText = findViewById(R.id.dniInput)
        val btnSelectActividad: Button = findViewById(R.id.btnSelectActivity)
        val txtAmount: EditText = findViewById(R.id.amount)
        val btnSend: MaterialButton = findViewById(R.id.send)

        btnSelectActividad.setOnClickListener {
            val intent = Intent(this, PopupSeleccionarActividad::class.java)
            activityResultLauncher.launch(intent) // Utilizamos el activityResultLauncher para lanzar ee popup
        }

        btnSend.setOnClickListener {
            var amount = 0.0
            val dni = txtDni.text.toString().trim()
            val amountStr = txtAmount.text.toString().trim()
            if(amountStr != ""){
                amount = amountStr.toDouble()
            }
            if(optionSelect.isNotEmpty() && dni.isNotEmpty() && amount != 0.0) {
                val noSocio: NoSocio? = noSocioController.getNoSocio(dni)

                ConfirmDialogUtils.showDairyPaymentDialog(this, noSocio, optionSelect, amount) { confirmed ->
                    if (confirmed) {
                        val (successEnroll, messageEnroll) = actividadController.paymentDiaryActividad(optionSelect.lowercase(), dni, amount)
                        txtDni.text.clear()
                        showEnrollDialog(messageEnroll, successEnroll)
                    } else {
                        // Acción si el usuario cancela
                        Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show()
                    }
                }
            } else{
                Toast.makeText(this, "Faltan completar datos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupUI(){
        // Personalizamos el header con el nombre del usuario
        val username = UserSessionUtil.getUserSession(this)
        val txtWelcome: TextView = findViewById(R.id.txtWelcome)
        txtWelcome.text = "Bienvenido! $username"

        //Asigno el Título de la pantalla. Solo es visible al correr la app
        val title = findViewById<TextView>(R.id.title_socio)
        title.text = "Pago Actividad Diaria"

        // Boton para volver al menu principal
        val btnBack : ImageButton = findViewById(R.id.back)
        backRedirect(this, btnBack)

        // Funcionalidad del botón Exit
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)
    }

    private fun btnSelectActivityOnClick(){
        val button: Button = findViewById(R.id.btnSelectActivity)
        button.setOnClickListener{
            val intent = Intent(this, PopupSeleccionarActividad::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())
        }
    }

    private fun showEnrollDialog(message: String, success: Boolean) {
        val dialog = CheckEnrollActividadDialogFragment.newInstance(message, success)
        dialog.show(supportFragmentManager, "CheckPaymentDialog")
    }
}