package com.example.clubdeportivo.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R
import com.example.clubdeportivo.repositories.UsuarioRepository

class LoginActivity : AppCompatActivity() {

    private lateinit var usuarioRepository: UsuarioRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val btnLogin: Button = findViewById(R.id.btnLogin)
        val txtUsername: EditText = findViewById(R.id.txtUsername)
        val txtPassword: EditText = findViewById(R.id.txtPassword)
        val btnTogglePassword: ImageButton = findViewById(R.id.btnTogglePassword)
        PasswordUtils.setupPasswordToggle(txtPassword, btnTogglePassword)
        usuarioRepository = UsuarioRepository(this)

        btnLogin.setOnClickListener {

            val username = txtUsername.text.toString()
            val password = txtPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val loginSuccess = usuarioRepository.login(username, password)

                if (loginSuccess) {
                    Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT)
                        .show()
                    txtUsername.text.clear()
                    txtPassword.text.clear()
                }
            }
        }
    }
}