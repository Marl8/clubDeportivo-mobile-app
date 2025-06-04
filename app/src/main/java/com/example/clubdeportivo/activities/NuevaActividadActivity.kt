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
import com.example.clubdeportivo.controllers.ActividadController
import com.example.clubdeportivo.entities.Actividad
import com.example.clubdeportivo.repositories.ActividadRepository
import com.example.clubdeportivo.repositories.NoSocioRepository
import com.example.clubdeportivo.repositories.SocioRepository
import com.example.clubdeportivo.utils.ClearFormUtils
import com.example.clubdeportivo.utils.UserMenuUtils
import com.example.clubdeportivo.utils.UserSessionUtil
import com.example.clubdeportivo.utils.backRedirect
import com.example.clubdeportivo.utils.setupLogoutButton
import com.google.android.material.button.MaterialButton

class NuevaActividadActivity : AppCompatActivity() {

    private lateinit var actividadController: ActividadController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.drawer_nueva_actividad)

        UserMenuUtils.setupDrawer(this)
        setUI()

        actividadController = ActividadController(
            ActividadRepository(this),
            SocioRepository(this), NoSocioRepository(this)
        )
        val formLayout: ViewGroup = findViewById(R.id.body)
        val txtName: EditText = findViewById(R.id.name_act)
        val txtValue: EditText = findViewById(R.id.value_act)
        val maxSocioQuota: EditText = findViewById(R.id.max_quota_socio)
        val maxNoSocioQuota: EditText = findViewById(R.id.max_quota_nosocio)
        val btnSend: MaterialButton = findViewById(R.id.send)
        val btnClear: ImageButton = findViewById(R.id.clear)

        btnSend.setOnClickListener {
            val name = txtName.text.toString().trim()
            val value = txtValue.text.toString().trim()
            val maxSocio = maxSocioQuota.text.toString().trim()
            val maxNoSocio = maxNoSocioQuota.text.toString().trim()

            val actividad: Actividad
            val success: Boolean
            if(name.isNotEmpty() && value.isNotEmpty() && maxSocio.isNotEmpty() && maxNoSocio.isNotEmpty()){
                val quotaActualSocio = maxSocio.toInt()
                val quotaActualNoSocio = maxNoSocio.toInt()
                actividad = Actividad(null, name, value.toDouble(), maxSocio.toInt(),maxNoSocio.toInt(),
                    quotaActualSocio, quotaActualNoSocio)
                success = actividadController.createActividad(actividad)
                if(success) {
                    ClearFormUtils.clearForm(formLayout)
                    Toast.makeText(this, "Inscripción exitosa", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error no ha podido completarse la operación.",
                        Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(
                    this, "Debe completar todos los campos.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnClear.setOnClickListener{
            ClearFormUtils.clearForm(formLayout)
        }
    }

    private fun setUI(){
        // Personalizamos el header con el nombre del usuario
        val username = UserSessionUtil.getUserSession(this)
        val txtWelcome: TextView = findViewById(R.id.txtWelcome)
        txtWelcome.text = "Bienvenido! $username"

        //Botón para cerrar sesión
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)

        // Boton para volver al menu principal
        val btnBack: ImageButton = findViewById(R.id.back)
        backRedirect(this, btnBack)
    }
}