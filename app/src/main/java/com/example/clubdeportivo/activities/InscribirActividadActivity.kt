package com.example.clubdeportivo.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R
import com.example.clubdeportivo.controllers.ActividadController
import com.example.clubdeportivo.controllers.NoSocioController
import com.example.clubdeportivo.controllers.SocioController
import com.example.clubdeportivo.fragments.CheckEnrollActividadDialogFragment
import com.example.clubdeportivo.repositories.ActividadRepository
import com.example.clubdeportivo.repositories.NoSocioRepository
import com.example.clubdeportivo.repositories.SocioRepository
import com.example.clubdeportivo.utils.*
import com.google.android.material.button.MaterialButton

class InscribirActividadActivity: AppCompatActivity() {

    private var optionSelect: String = ""
    private var client: String = ""
    private var name: String = ""
    private var isConfirm: Boolean = false
    private lateinit var actividadController: ActividadController
    private lateinit var socioController: SocioController
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
        setContentView(R.layout.drawer_inscribir_actividad)

        UserMenuUtils.setupDrawer(this)
        setupUI()
        btnSelectActivityOnClick()

        actividadController = ActividadController(ActividadRepository(this),
            SocioRepository(this), NoSocioRepository(this))
        socioController = SocioController(SocioRepository(this))
        noSocioController = NoSocioController(NoSocioRepository(this))
        val txtDni: EditText = findViewById(R.id.dniInput)
        val btnSelectActividad: Button = findViewById(R.id.btnSelectActivity)
        val radioGroup: RadioGroup = findViewById(R.id.radioGroupClient)
        val btnSend: MaterialButton = findViewById(R.id.send)

        var success: Boolean
        var message: String

        radioGroup.setOnCheckedChangeListener { group, radioGroupClient ->
            val selectedRadioButton: RadioButton = findViewById(radioGroupClient)
            client = selectedRadioButton.text.toString().trim()
        }

        // Leer valor inicial por defecto
        val checkedRadioButtonId = radioGroup.checkedRadioButtonId
        if (checkedRadioButtonId != -1) {
            val selectedRadioButton: RadioButton = findViewById(checkedRadioButtonId)
            client = selectedRadioButton.text.toString().trim()
        }

        btnSelectActividad.setOnClickListener {
            val intent = Intent(this, PopupSeleccionarActividad::class.java)
            activityResultLauncher.launch(intent) // Utilizamos el activityResultLauncher para lanzar ee popup
        }

        btnSend.setOnClickListener {
            val dni = txtDni.text.toString().trim()
            if(optionSelect.isNotEmpty() && dni.isNotEmpty()) {
                if (client == "Socio") {
                    val socio = socioController.getSocio(dni)
                    if (socio != null) {
                        name = "${socio.name} ${socio.lastName}"
                    }
                    if(isConfirm){
                        val (successEnroll, messageEnroll) = actividadController.enrollSocioActividad(optionSelect.lowercase(), dni)
                        message = messageEnroll
                        success = successEnroll
                        showEnrollDialog(message, success)
                    }else {
                        showConfirmDialog()
                    }
                } else if(client == "No Socio"){
                    val noSocio = noSocioController.getNoSocio(dni)
                    if(noSocio != null){
                        name = "${noSocio.name} ${noSocio.lastName}"
                    }
                    if(isConfirm){
                        val (successEnroll, messageEnroll) = actividadController.enrollNoSocioActividad(optionSelect.lowercase(), dni)
                        message = messageEnroll
                        success = successEnroll
                        showEnrollDialog(message, success)
                    }else {
                        showConfirmDialog()
                    }
                }
            } else{
                Toast.makeText(this, "Faltan completar datos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun btnSelectActivityOnClick(){
        val button: Button = findViewById(R.id.btnSelectActivity)
        button.setOnClickListener{
            val intent = Intent(this, PopupSeleccionarActividad::class.java)
            activityResultLauncher.launch(intent)
        }
    }

    private fun setupUI(){
        //Asigno el Título de la pantalla. Solo es visible al correr la app
        val title = findViewById<TextView>(R.id.title_socio)
        title.text = "Inscribir Actividad"

        // Personalizamos el header con el nombre del usuario
        val username = UserSessionUtil.getUserSession(this)
        val role = UserSessionUtil.getUserRole(this)
        val txtWelcome: TextView = findViewById(R.id.txtWelcome)
        txtWelcome.text = "Bienvenido! $username"

        // Boton para volver al menu principal
        val btnBack : ImageButton = findViewById(R.id.back)
        backRedirect(this, btnBack)

        // Funcionalidad del botón Exit
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)

    }

    private fun showConfirmDialog(){
        ConfirmPaymentDialogUtils.showEnrollActividadDialog(this, name, optionSelect) { confirmed ->
            if (confirmed) {
                isConfirm = confirmed
                Toast.makeText(this, "Datos confirmados.", Toast.LENGTH_SHORT).show()
            } else {
                // Acción si el usuario cancela
                Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showEnrollDialog(message: String, success: Boolean) {
        val dialog = CheckEnrollActividadDialogFragment.newInstance(message, success)
        dialog.show(supportFragmentManager, "CheckPaymentDialog")
    }
}