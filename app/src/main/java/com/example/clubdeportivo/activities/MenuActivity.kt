package com.example.clubdeportivo.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R
import com.example.clubdeportivo.fragments.AptoFisicoDialogFragment
import com.example.clubdeportivo.utils.*

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.drawer_main_menu)

        UserMenuUtils.setupDrawer(this)

        val btnSocio: ImageButton = findViewById(R.id.btnSocio)
        val btnNoSocio: ImageButton = findViewById(R.id.btnNoSocio)
        val btnCarnet: ImageButton = findViewById(R.id.btnCarnet)
        val btnCuota: ImageButton = findViewById(R.id.btnPagoCuota)
        val btnPagoDiario: ImageButton = findViewById(R.id.btnPagoDiario)
        val btnListaMorosos: ImageButton = findViewById(R.id.btnListaMorosos)
        val btnListaVencimientos: ImageButton = findViewById(R.id.btnListaVencimientos)
        val btnActividad: ImageButton = findViewById(R.id.btnActividad)

        val txtWelcome: TextView = findViewById(R.id.txtWelcome)

        // Personalizamos el header con el nombre del usuario
        val username = UserSessionUtil.getUserSession(this)
        txtWelcome.text = "Bienvenido! $username"

        //Botón para cerrar sesión
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)

        var isSocio: Boolean

        btnSocio.setOnClickListener{
            isSocio = true
            showAptoFisicoPopup(isSocio)
        }

        btnNoSocio.setOnClickListener{
            isSocio = false
            showAptoFisicoPopup(isSocio)
        }

        // ActivityOptions.makeCustomAnimation -> suaviza transiciones entre pantallas
        btnCarnet.setOnClickListener {
            val intent = Intent(this, CarnetActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())

        }

        btnActividad.setOnClickListener{
            val intent = Intent(this, InscribirActividadActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())
        }

        btnCuota.setOnClickListener{
            val intent = Intent(this, PagarCuotaActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())
        }

        btnPagoDiario.setOnClickListener{
            val intent = Intent(this, PagarActividadDiariaActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())
        }

        btnListaMorosos.setOnClickListener {
            val intent = Intent(this, ListaMorososActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())
        }

        btnListaVencimientos.setOnClickListener {
            val intent = Intent(this, ListaVencimientosActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())
        }
    }

    private fun showAptoFisicoPopup(isSocio: Boolean) {
        val dialog = AptoFisicoDialogFragment.newInstance(isSocio)
        dialog.show(supportFragmentManager, "AptoFisicoDialog")
    }
}