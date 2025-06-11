package com.example.clubdeportivo.activities

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clubdeportivo.R
import com.example.clubdeportivo.controllers.SocioController
import com.example.clubdeportivo.entities.dto.SocioExpirationDayDto
import com.example.clubdeportivo.repositories.NoSocioRepository
import com.example.clubdeportivo.repositories.SocioRepository
import com.example.clubdeportivo.utils.GenericRecyclerAdapterUtil
import com.example.clubdeportivo.utils.StateSocioDialogUtils
import com.example.clubdeportivo.utils.UserMenuUtils
import com.example.clubdeportivo.utils.UserSessionUtil
import com.example.clubdeportivo.utils.backRedirect
import com.example.clubdeportivo.utils.setupLogoutButton
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId

class ListaVencimientosActivity : AppCompatActivity() {

    private lateinit var socioController: SocioController
    private lateinit var socios: MutableList<SocioExpirationDayDto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.drawer_list_vencimientos)

        UserMenuUtils.setupDrawer(this)
        AndroidThreeTen.init(this)
        setupUI()

        socioController = SocioController(SocioRepository(this), NoSocioRepository(this))
        socios = socioController.getListSociosByExpiationDay(
            LocalDate.now(
                ZoneId.of(
            "America/Argentina/Buenos_Aires")))

        // Configurar RecyclerView
        val rvPaymentDay: RecyclerView = findViewById(R.id.rvMorosos)
        rvPaymentDay.layoutManager = LinearLayoutManager(this)

        /**
         * Adapter
         *
         * Adapter: Convierte los datos en elementos visuales
         *
         * RecyclerView: Muestra los elementos en pantalla
         * */

        if(socios.isEmpty()) {
            Toast.makeText(this, "No hay socio con vencimientos en el día",
                Toast.LENGTH_SHORT).show()
        }

        rvPaymentDay.adapter = GenericRecyclerAdapterUtil.createAdapter(
            items = socios,
            layoutResId = R.layout.component_list_clientes,
            onBindView = { itemView, socio, position ->
                itemView.apply {
                    findViewById<TextView>(R.id.txtNombre).text = "${socio.name} ${socio.lastName}"
                    findViewById<TextView>(R.id.txtDni).text = "DNI: ${socio.dni}"
                    findViewById<TextView>(R.id.txtFech_vencimiento).text = "Vencimiento: ${socio.expirationDay}"

                    findViewById<ImageButton>(R.id.additionalInfo).setOnClickListener {
                        StateSocioDialogUtils.showDialogSocioState(
                            context = context,
                            socio = socio
                        )
                    }
                }
            }
        )
    }

    private fun setupUI(){
        // Personalizamos el header con el nombre del usuario
        val username = UserSessionUtil.getUserSession(this)
        val txtWelcome: TextView = findViewById(R.id.txtWelcome)
        txtWelcome.text = "Bienvenido! $username"

        // Boton para volver al menu principal
        val btnBack : ImageButton = findViewById(R.id.back)
        backRedirect(this, btnBack)

        // Funcionalidad del botón Exit
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)

        // Setea el titulo dinámicamente
        val title = findViewById<TextView>(R.id.title_socio)
        title.text = "Lista de Vencimientos"
    }
}