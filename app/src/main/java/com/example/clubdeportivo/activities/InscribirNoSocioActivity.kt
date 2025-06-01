package com.example.clubdeportivo.activities

import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R
import com.example.clubdeportivo.controllers.NoSocioController
import com.example.clubdeportivo.entities.NoSocio
import com.example.clubdeportivo.repositories.NoSocioRepository
import com.example.clubdeportivo.utils.ClearFormUtils
import com.example.clubdeportivo.utils.UserSessionUtil
import com.example.clubdeportivo.utils.backRedirect
import com.example.clubdeportivo.utils.setupLogoutButton
import com.google.android.material.button.MaterialButton

class InscribirNoSocioActivity : AppCompatActivity() {

    private lateinit var noSocioController: NoSocioController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inscribir_no_socio)

        setupUI()

        noSocioController = NoSocioController(NoSocioRepository(this))
        val formLayout: ViewGroup = findViewById(R.id.body)
        val btnClear: ImageButton = findViewById(R.id.clear)
        val btnSend: MaterialButton = findViewById(R.id.send)
        val txtName: EditText = findViewById(R.id.name)
        val txtLastName: EditText = findViewById(R.id.lastName)
        val txtDni: EditText = findViewById(R.id.dni)
        val txtEmail: EditText = findViewById(R.id.email)
        val txtPhone: EditText = findViewById(R.id.phoneNumber)

        // Recupera el valor para apto físico del Intent del PopupAptoFisicoActivity
        val isAptoFisico = intent.getBooleanExtra("aptoFisico", false)

        btnSend.setOnClickListener {
            val name = txtName.text.toString().trim()
            val lastName = txtLastName.text.toString().trim()
            val dni = txtDni.text.toString().trim()
            val email = txtEmail.text.toString().trim()
            val phone = txtPhone.text.toString().trim()

            if (name.isEmpty() || lastName.isEmpty() || dni.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val noSocio = NoSocio(null, isAptoFisico, name, lastName, dni, email, phone)
                val success = noSocioController.enrollSocio(noSocio)
                if (success) {
                    ClearFormUtils.clearForm(formLayout)
                    Toast.makeText(this, "Inscripción exitosa", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error no ha podido completarse la operación.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnClear.setOnClickListener{
            ClearFormUtils.clearForm(formLayout)
        }
    }

    private fun setupUI(){
        // Personalizamos el header con el nombre del usuario
        val username = UserSessionUtil.getUserSession(this)
        val role = UserSessionUtil.getUserRole(this)
        val txtWelcome: TextView = findViewById(R.id.txtWelcome)
        txtWelcome.text = "Bienvenido! $username"

        // Boton para volver al menu principal
        val btnBack : ImageButton = findViewById(R.id.back)
        backRedirect(this, btnBack)

        //Botón para cerrar sesión
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)
    }
}