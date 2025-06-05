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
import com.example.clubdeportivo.controllers.NoSocioController
import com.example.clubdeportivo.entities.dto.NoSocioEnabledDto
import com.example.clubdeportivo.repositories.NoSocioRepository
import com.example.clubdeportivo.utils.StateSocioDialogUtils
import com.example.clubdeportivo.utils.UserMenuUtils
import com.example.clubdeportivo.utils.UserSessionUtil
import com.example.clubdeportivo.utils.backRedirect
import com.example.clubdeportivo.utils.setupLogoutButton
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId

class ListaDiariaNoSociosHabilitadosActivity : AppCompatActivity() {

    private lateinit var noSocioController: NoSocioController
    private lateinit var noSocios: List<NoSocioEnabledDto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.drawer_list_nosocios_habilitados)

        UserMenuUtils.setupDrawer(this)
        AndroidThreeTen.init(this)
        setupUI()

        noSocioController = NoSocioController(NoSocioRepository(this))
        noSocios = noSocioController.getListNoSociosDayEnabled(
            LocalDate.now(
                ZoneId.of(
                    "America/Argentina/Buenos_Aires")))


        // Configurar RecyclerView
        val rvNoSocios: RecyclerView = findViewById(R.id.rvNoSocios)
        rvNoSocios.layoutManager = LinearLayoutManager(this)

        if(noSocios.isEmpty()) {
            Toast.makeText(
                this, "No hay no socios habilitados en el día",
                Toast.LENGTH_SHORT
            ).show()
        }
        rvNoSocios.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            // 1. onCreateViewHolder - Infla el layout de cada ítem
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = layoutInflater.inflate(R.layout.component_list_clientes, parent, false)
                return object : RecyclerView.ViewHolder(view){}
            }

            // 2. onBindViewHolder - Asigna los datos a las vistas
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

                if(noSocios.isNotEmpty()) {
                    val noSocio = noSocios[position]
                    holder.itemView.apply {
                        findViewById<TextView>(R.id.txtNombre).text =
                            "${noSocio.nameNoSocio} ${noSocio.lastName}"
                        findViewById<TextView>(R.id.txtDni).text = "DNI: ${noSocio.dni}"
                        findViewById<TextView>(R.id.txtFech_vencimiento).text =
                            "Día Habilitado: ${noSocio.enableDay}"

                        findViewById<ImageButton>(R.id.additionalInfo).setOnClickListener {
                            StateSocioDialogUtils.showDialogNoSociosEnabled(
                                context = context,
                                noSocio = noSocio)
                        }
                    }
                }
            }
            override fun getItemCount(): Int = noSocios.size
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

        // Funcionalidad del botón Exit
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)

        // Setea el titulo dinámicamente
        val title = findViewById<TextView>(R.id.title_socio)
        title.text = "Lista de No Socios Habilitados"
    }
}