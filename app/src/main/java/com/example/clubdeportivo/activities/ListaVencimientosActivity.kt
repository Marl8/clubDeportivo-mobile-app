package com.example.clubdeportivo.activities

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clubdeportivo.R
import com.example.clubdeportivo.utils.setupLogoutButton

class ListaVencimientosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_vencimientos)

        // Funcionalidad del botón Exit
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)

        // Setea el titulo dinámicamente
        val title = findViewById<TextView>(R.id.title_socio)
        title.text = "Lista de Vencimientos"

        // Configurar RecyclerView
        val rvMorosos: RecyclerView = findViewById(R.id.rvMorosos)
        rvMorosos.layoutManager = LinearLayoutManager(this)

        // Datos de prueba (nombre, dni, fecha)
        val socios = listOf(
            Triple("Juan Pérez", "12345678", "15/05/2025"),
            Triple("María Fernandez", "87654321", "20/03/2025"),
            Triple("Carlos Martinez", "56781234", "10/04/2025"),
            Triple("Ana Valdez", "43218765", "25/04/2025"),
            Triple("Anibal Parra", "33698520", "30/04/2025"),
            Triple("Segio Sosa", "54890365", "15/04/2025"),
            Triple("Fabio Lamas", "29852047", "12/03/2025"),
            Triple("Lorena Gonzalez", "40682127", "06/02/2025")
        )

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
                val (nombre, dni, fechaVenc) = socios[position]

                holder.itemView.apply {
                    findViewById<TextView>(R.id.editTextText).text = nombre
                    findViewById<TextView>(R.id.txtDni).text = "DNI: $dni"
                    findViewById<TextView>(R.id.txtFech_vencimiento).text = "Vencimiento: $fechaVenc"
                }
            }
            override fun getItemCount(): Int = socios.size
        }
    }
}