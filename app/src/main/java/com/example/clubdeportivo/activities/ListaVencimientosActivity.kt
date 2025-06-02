package com.example.clubdeportivo.activities

import android.os.Bundle
import android.view.ViewGroup
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
import com.example.clubdeportivo.repositories.SocioRepository
import com.example.clubdeportivo.utils.StateSocioDialogUtils
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
        setContentView(R.layout.activity_lista_vencimientos)

        AndroidThreeTen.init(this)
        setupUI()

        socioController = SocioController(SocioRepository(this))
        socios = socioController.listSociosByExpiationDay(
            LocalDate.now(
                ZoneId.of(
            "America/Argentina/Buenos_Aires")))

        // Configurar RecyclerView
        val rvMorosos: RecyclerView = findViewById(R.id.rvMorosos)
        rvMorosos.layoutManager = LinearLayoutManager(this)

        /**
         * Adapter
         *
         * Adapter: Convierte los datos en elementos visuales
         *
         * RecyclerView: Muestra los elementos en pantalla
         * */

        rvMorosos.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            // 1. onCreateViewHolder - Infla el layout de cada ítem
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = layoutInflater.inflate(R.layout.component_list_socios, parent, false)
                return object : RecyclerView.ViewHolder(view){}
            }

            // 2. onBindViewHolder - Asigna los datos a las vistas
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

                if(socios.isNotEmpty()) {
                    val socio = socios[position]

                    holder.itemView.apply {
                        findViewById<TextView>(R.id.txtNombre).text =
                            "${socio.name} ${socio.lastName}"
                        findViewById<TextView>(R.id.txtDni).text = "DNI: ${socio.dni}"
                        findViewById<TextView>(R.id.txtFech_vencimiento).text =
                            "Vencimiento: ${socio.expirationDay}"

                        findViewById<ImageButton>(R.id.additionalInfo).setOnClickListener {
                            StateSocioDialogUtils.showDialogSocioState(
                                context = context,
                                socio = socio
                            )
                        }
                    }
                }else {
                    Toast.makeText(holder.itemView.context, "No hay socio con vencimientos en el día",
                        Toast.LENGTH_SHORT).show()
                }
            }
            override fun getItemCount(): Int = socios.size
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

        // Funcionalidad del botón Exit
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)

        // Setea el titulo dinámicamente
        val title = findViewById<TextView>(R.id.title_socio)
        title.text = "Lista de Vencimientos"
    }
}