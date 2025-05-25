package com.example.clubdeportivo.activities

import android.content.Intent
import android.widget.Toast
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import com.example.clubdeportivo.R
import com.example.clubdeportivo.utils.UserSessionUtil
import com.example.clubdeportivo.utils.backRedirect
import com.example.clubdeportivo.utils.setupLogoutButton
import com.google.android.material.button.MaterialButton
import androidx.core.graphics.createBitmap
import com.example.clubdeportivo.controllers.SocioController
import com.example.clubdeportivo.entities.Socio
import com.example.clubdeportivo.repositories.SocioRepository
import java.io.File
import java.io.FileOutputStream

class CarnetActivity : AppCompatActivity() {

    private lateinit var socioController: SocioController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carnet)

        setUI()

        socioController = SocioController(SocioRepository(this))
        val txtDniSocio: EditText = findViewById(R.id.dniCliente)
        val btnSearch: MaterialButton = findViewById(R.id.search)
        val btnCarnet: MaterialButton = findViewById(R.id.btnCarnet)

        var socio: Socio?
        btnSearch.setOnClickListener {
            socio = socioController.getSocio(txtDniSocio.text.toString())
            txtDniSocio.text.clear()

            if (socio != null) {
                val numSocio: TextView = findViewById(R.id.num_socio)
                val stateSocio: TextView = findViewById(R.id.estado)
                val nameSocio: TextView = findViewById(R.id.nombre_socio)
                val lastNameSocio: TextView = findViewById(R.id.apellido)
                val dniSocio: TextView = findViewById(R.id.dni)

                numSocio.text = socio?.idSocio.toString()
                nameSocio.text = socio?.name
                lastNameSocio.text = socio?.lastName
                dniSocio.text = socio?.dni
                val state = socio?.stateSocio
                if (state == true) stateSocio.text = "Activo"
                else stateSocio.text = "Inactivo"

                Toast.makeText(this, "Socio cargado correctamente", Toast.LENGTH_LONG).show()
            }else
                Toast.makeText(this, "Socio inexistente", Toast.LENGTH_LONG).show()
        }
        btnCarnet.setOnClickListener {
            val carnet: CardView = findViewById(R.id.carnet)
            carnet.post {
                generateCarnet()
                }
            }
        }

    private fun generateCarnet() {
        val pdfDocument = PdfDocument()
        try {
            val file = File(filesDir, "carnet_${System.currentTimeMillis()}.pdf")

            val carnet: CardView = findViewById(R.id.carnet)
            val bitmap = createBitmap(carnet.width, carnet.height)
            carnet.draw(Canvas(bitmap))

            val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            page.canvas.drawBitmap(bitmap, 0f, 0f, null)
            pdfDocument.finishPage(page)

            FileOutputStream(file).use { fos -> // fos = FileOutputStream
                pdfDocument.writeTo(fos)
            }
            bitmap.recycle()

            // Verifica que se guardó
            if (file.exists()) {
                Toast.makeText(this, "PDF guardado", Toast.LENGTH_LONG).show()
                shareFile(file)
            } else {
                Toast.makeText(this, "Error: No se pudo guardar el PDF", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("PDF_ERROR", "Error al generar PDF", e)
        } finally {
            pdfDocument.close()
        }
    }

    private fun shareFile(file: File) {
        val uri = FileProvider.getUriForFile(this, "$packageName.provider", file)
        val intent = Intent.createChooser(
            Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }, "Compartir Carnet"
        )
        startActivity(intent)
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

        // Setea el titulo dinámicamente
        val title = findViewById<TextView>(R.id.title_socio)
        title.text = "Carnet de Socio"
    }
}