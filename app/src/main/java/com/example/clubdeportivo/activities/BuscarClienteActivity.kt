package com.example.clubdeportivo.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.clubdeportivo.R
import com.example.clubdeportivo.controllers.NoSocioController
import com.example.clubdeportivo.controllers.SocioController
import com.example.clubdeportivo.entities.NoSocio
import com.example.clubdeportivo.entities.Socio
import com.example.clubdeportivo.repositories.NoSocioRepository
import com.example.clubdeportivo.repositories.SocioRepository
import com.example.clubdeportivo.utils.UserMenuUtils
import com.example.clubdeportivo.utils.UserSessionUtil
import com.example.clubdeportivo.utils.backRedirect
import com.example.clubdeportivo.utils.setupLogoutButton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BuscarClienteActivity : AppCompatActivity() {

    private lateinit var socioController: SocioController
    private lateinit var noSocioController: NoSocioController
    private lateinit var cardCliente: CardView
    private lateinit var txtId: TextView
    private lateinit var txtName: TextView
    private lateinit var txtDni: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtPhone: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.drawer_buscar_cliente)

        UserMenuUtils.setupDrawer(this)
        setUI()

        socioController = SocioController(SocioRepository(this))
        noSocioController = NoSocioController(NoSocioRepository(this))
        cardCliente = findViewById(R.id.card_cliente)
        txtId = findViewById(R.id.id_search)
        txtName = findViewById(R.id.name_search)
        txtDni = findViewById(R.id.dni_search)
        txtEmail = findViewById(R.id.email_search)
        txtPhone = findViewById(R.id.phone_search)
        val txtSearch: AutoCompleteTextView = findViewById(R.id.dniInput)
        val radioGroupClient: RadioGroup = findViewById(R.id.radioGroupClient)

        // Configuración del AutoCompleteTextView
        txtSearch.apply {
            setAdapter(
                ArrayAdapter(this@BuscarClienteActivity,
                android.R.layout.simple_dropdown_item_1line,
                mutableListOf<String>())
            )

            setOnItemClickListener { _, _, position, _ ->
                val selectedDni = adapter.getItem(position)
                selectedDni?.let { findClient(it.toString(), radioGroupClient) }
            }
        }

        txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val searchClient = s.toString().trim()
                if (searchClient.length == 8) {
                    findClient(searchClient, radioGroupClient)
                } else {
                    hideFormWithAnimation()
                }
            }
        })

        // Manejar cambio entre Socio/No Socio
        radioGroupClient.setOnCheckedChangeListener { _, checkedId ->
            if (txtSearch.text.length == 8) {
                findClient(txtSearch.text.toString(), radioGroupClient)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun findClient(dni: String, radioGroupClient: RadioGroup) {
        val isSocio = radioGroupClient.checkedRadioButtonId == R.id.radioButtonSocio

        GlobalScope.launch(Dispatchers.IO) {
            try {
                if (isSocio) {
                    val socio = socioController.getSocio(dni)
                    withContext(Dispatchers.Main) {
                        socio?.let {
                            showSocio(it)
                        } ?: run {
                            showNotFound(dni)
                        }
                    }
                } else {
                    val noSocio = noSocioController.getNoSocio(dni)
                    withContext(Dispatchers.Main) {
                        noSocio?.let {
                            showNoSocio(it)
                        } ?: run {
                            showNotFound(dni)
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showSearchError(e)
                }
            }
        }
    }

    private fun showSocio(socio: Socio) {
        txtId.text = socio.idSocio.toString()
        txtName.text = "${socio.name} ${socio.lastName}"
        txtDni.text = socio.dni
        txtEmail.text = socio.email
        txtPhone.text = socio.phone
        showFormWithAnimation()
    }

    private fun showNoSocio(noSocio: NoSocio) {
        txtId.text = noSocio.idNoSocio.toString()
        txtName.text = "${noSocio.name} ${noSocio.lastName}"
        txtDni.text = noSocio.dni
        txtEmail.text = noSocio.email
        txtPhone.text = noSocio.phone
        showFormWithAnimation()
    }

    private fun showNotFound(dni: String) {
        hideFormWithAnimation()
        Toast.makeText(
            this,
            "No se encontró cliente con DNI $dni",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showSearchError(e: Exception) {
        hideFormWithAnimation()
        Toast.makeText(
            this,
            "Error al buscar: ${e.message}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showFormWithAnimation() {
        cardCliente.apply {
            visibility = View.VISIBLE
            alpha = 0f
            translationY = 20f

            animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(300)
                .start()
        }
    }

    private fun hideFormWithAnimation() {
        cardCliente.animate()
            .alpha(0f)
            .translationY(20f)
            .setDuration(200)
            .withEndAction {
                cardCliente.visibility = View.GONE
            }
            .start()
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
        title.text = "Buscar Cliente"
    }
}