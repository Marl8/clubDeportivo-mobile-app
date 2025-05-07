package com.example.clubdeportivo.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clubdeportivo.R
import com.example.clubdeportivo.utils.setupLogoutButton

class ListaMorososActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_morosos)

        // Funcionalidad del botón Exit
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)

        // Setea el titulo dinámicamente
        val title = findViewById<TextView>(R.id.title_socio)
        title.text = "Lista de Morosos"

        // Configurar RecyclerView
        val rvMorosos: RecyclerView = findViewById(R.id.rvMorosos)
        rvMorosos.layoutManager = LinearLayoutManager(this)

        // Datos de prueba (nombre, dni, fecha)
        val socios = listOf(
            Triple("Juan Pérez", "12345678", "15/05/1985"),
            Triple("María García", "87654321", "20/03/1990"),
            Triple("Carlos López", "56781234", "10/11/1978"),
            Triple("Ana Martínez", "43218765", "25/09/1982"),
            Triple("Anibal Sanchez", "33698520", "30/07/1985"),
            Triple("Segio Sosa", "54890365", "15/06/1999"),
            Triple("Fabio Lazo", "29852047", "12/09/1981"),
            Triple("Silvia Gonzalez", "40682127", "06/02/1991")
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
                val (nombre, dni, fechaNac) = socios[position]

                holder.itemView.apply {
                    findViewById<TextView>(R.id.editTextText).text = nombre
                    findViewById<TextView>(R.id.txtDni).text = "DNI: $dni"
                    findViewById<TextView>(R.id.txtFech_nacimiento).text = "Nacimiento: $fechaNac"
                }
            }
            override fun getItemCount(): Int = socios.size
        }
    }
}

