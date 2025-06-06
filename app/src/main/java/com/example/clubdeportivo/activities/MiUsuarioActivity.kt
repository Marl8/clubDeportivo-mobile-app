package com.example.clubdeportivo.activities

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R
import com.example.clubdeportivo.controllers.UsuarioController
import com.example.clubdeportivo.entities.Usuario
import com.example.clubdeportivo.repositories.UsuarioRepository
import com.example.clubdeportivo.utils.ConfirmDialogUtils
import com.example.clubdeportivo.utils.SecurityUtils
import com.example.clubdeportivo.utils.UserMenuUtils
import com.example.clubdeportivo.utils.UserSessionUtil
import com.example.clubdeportivo.utils.backRedirect
import com.example.clubdeportivo.utils.setupLogoutButton
import com.google.android.material.button.MaterialButton

class MiUsuarioActivity : AppCompatActivity() {

    private lateinit var usuarioController: UsuarioController
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.drawer_mi_usuario)

        UserMenuUtils.setupDrawer(this)
        setupUI()

        usuarioController = UsuarioController(UsuarioRepository(this))
        val user: Usuario? = usuarioController.findUsuarioByUsername(username)

        val btnSend: MaterialButton = findViewById(R.id.send)
        val txtName: EditText = findViewById(R.id.name)
        val txtLastName: EditText = findViewById(R.id.lastName)
        val txtDni: EditText = findViewById(R.id.dni)
        val txtEmail: EditText = findViewById(R.id.email)
        val txtPhone: EditText = findViewById(R.id.phoneNumber)
        val txtUsername: EditText = findViewById(R.id.username)
        val txtPassword: EditText = findViewById(R.id.password)

        if (user != null) {
            txtName.setText(user.name)
            txtLastName.setText(user.lastName)
            txtDni.setText(user.dni)
            txtEmail.setText(user.email)
            txtPhone.setText(user.phone)
            txtUsername.setText(user.username)
        }
        txtPassword.setOnClickListener {
            txtPassword.text.clear()
        }

        btnSend.setOnClickListener {
            val name = txtName.text.toString().trim()
            val lastname = txtLastName.text.toString().trim()
            val dni = txtDni.text.toString().trim()
            val email = txtEmail.text.toString().trim()
            val phone = txtPhone.text.toString().trim()
            val username = txtUsername.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            if(name.isNotBlank() && lastname.isNotBlank() && dni.isNotBlank() && email.isNotBlank()
                && phone.isNotBlank() && username.isNotBlank() && password.isNotBlank()){

                val passwordHash = SecurityUtils.sha256(txtPassword.text.toString())
                val userEdit = user?.let { it1 ->
                    Usuario(user.idUsuario, username, passwordHash, it1.rol, name, lastname, dni,
                        email, phone)
                }

                /**
                 *  Llamada asicronica con una lambda
                 * Utilizamos la forma simplificado de Kotlin
                 * showConfirmDialog(userEdit) { confirmed -> ...} que es lo mismo que hacer
                 * showConfirmDialog(userEdit, { confirmed -> ...})
                 */

                showConfirmDialog(userEdit) { confirmed ->
                    if (confirmed) {
                        val success = userEdit?.let { us -> usuarioController.updateUsuario(us) } == true
                        if(success) {
                            Toast.makeText(this, "Usuario modificado con éxito", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Ocurrió un error en la operación", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Debe completar todos los campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupUI() {

        // Personalizamos el header con el nombre del usuario
        username = UserSessionUtil.getUserSession(this)
        val txtWelcome: TextView = findViewById(R.id.txtWelcome)
        txtWelcome.text = "Bienvenido! $username"

        // Botón para volver al menu principal
        val btnBack: ImageButton = findViewById(R.id.back)
        backRedirect(this, btnBack)

        //Botón para cerrar sesión
        val btnExit: ImageButton = findViewById(R.id.btnExit)
        setupLogoutButton(this, btnExit)

        // Setea el titulo dinámicamente
        val title = findViewById<TextView>(R.id.title_socio)
        title.text = "Mi Usuario"
    }

    private fun showConfirmDialog(userEdit: Usuario?, onConfirmed: (Boolean) -> Unit) {
        ConfirmDialogUtils.showUpdateUsuarioDialog(this) { confirmed ->
            onConfirmed(confirmed)
        }
    }
}