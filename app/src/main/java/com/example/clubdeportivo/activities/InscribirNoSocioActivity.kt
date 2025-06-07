package com.example.clubdeportivo.activities

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.clubdeportivo.R
import com.example.clubdeportivo.controllers.NoSocioController
import com.example.clubdeportivo.entities.NoSocio
import com.example.clubdeportivo.repositories.NoSocioRepository
import com.example.clubdeportivo.repositories.SocioRepository
import com.example.clubdeportivo.utils.ClearFormUtils
import com.example.clubdeportivo.utils.UserMenuUtils
import com.example.clubdeportivo.utils.UserSessionUtil
import com.example.clubdeportivo.utils.backRedirect
import com.example.clubdeportivo.utils.setupLogoutButton
import com.google.android.material.button.MaterialButton


class InscribirNoSocioActivity : AppCompatActivity() {

    private lateinit var noSocioController: NoSocioController
    private lateinit var searchLayout: ViewGroup
    private lateinit var txtId: TextView
    private lateinit var txtNameSearch: TextView
    private lateinit var txtDniSearch: TextView
    private lateinit var txtEmailSearch: TextView
    private lateinit var txtPhoneSearch: TextView
    private lateinit var cardCliente: CardView
    private lateinit var btnOk: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.drawer_inscribir_nosocios)

        UserMenuUtils.setupDrawer(this)
        setupUI()

        noSocioController = NoSocioController(NoSocioRepository(this), SocioRepository(this))
        val formLayout: ViewGroup = findViewById(R.id.form_container)
        val btnClear: ImageButton = findViewById(R.id.clear)
        val btnSend: MaterialButton = findViewById(R.id.send)
        val txtName: EditText = findViewById(R.id.name)
        val txtLastName: EditText = findViewById(R.id.lastName)
        val txtDni: EditText = findViewById(R.id.dni)
        val txtEmail: EditText = findViewById(R.id.email)
        val txtPhone: EditText = findViewById(R.id.phoneNumber)

        searchLayout = findViewById(R.id.info_container)
        txtId = findViewById(R.id.id_search)
        txtNameSearch = findViewById(R.id.name_search)
        txtDniSearch  = findViewById(R.id.dni_search)
        txtEmailSearch = findViewById(R.id.email_search)
        txtPhoneSearch = findViewById(R.id.phone_search)
        cardCliente = findViewById(R.id.card_cliente)
        btnOk = findViewById(R.id.aceptar)

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
                val (success, message) = noSocioController.enrollNoSocio(noSocio)
                if (success) {
                    ClearFormUtils.clearForm(formLayout)
                    val noSocioFound = noSocioController.getNoSocio(dni)
                    showNoSocio(noSocioFound)
                    // Ocultar formulario con animación
                    formLayout.animate()
                        .alpha(0f)
                        .translationY(-50f)
                        .setDuration(300)
                        .withEndAction {
                            formLayout.visibility = View.GONE
                        }
                        .start()
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnOk.setOnClickListener {
            searchLayout.visibility = View.GONE
            formLayout.visibility = View.VISIBLE
            formLayout.alpha = 1f
            formLayout.translationY = 0f
        }

        btnClear.setOnClickListener{
            ClearFormUtils.clearForm(formLayout)
        }
    }

    private fun setupUI(){
        // Personalizamos el header con el nombre del usuario
        val username = UserSessionUtil.getUserSession(this)
        val txtWelcome: TextView = findViewById(R.id.txtWelcome)
        txtWelcome.text = "Bienvenido! $username"

        // Boton para volver al menu principal
        val btnBack : ImageButton = findViewById(R.id.back)
        backRedirect(this, btnBack)

        //Botón para cerrar sesión
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)
    }

    private fun showNoSocio(noSocio: NoSocio?) {
        if (noSocio != null){
            txtId.text = noSocio.idNoSocio.toString()
            txtNameSearch.text = "${noSocio.name} ${noSocio.lastName}"
            txtDniSearch.text = noSocio.dni
            txtEmailSearch.text = noSocio.email
            txtPhoneSearch.text = noSocio.phone

            searchLayout.visibility = View.VISIBLE
            cardCliente.visibility = View.VISIBLE

            searchLayout.alpha = 0f
            searchLayout.translationY = 20f

            searchLayout.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(300)
                .start()
        }else{
            Toast.makeText(this, "Ocurrió un error.", Toast.LENGTH_SHORT).show()
        }
    }
}