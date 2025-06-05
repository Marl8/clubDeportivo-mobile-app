package com.example.clubdeportivo.controllers

import android.content.Context
import com.example.clubdeportivo.repositories.UsuarioRepository

class LoginController(private val context: Context, private val usuarioRepository: UsuarioRepository) {

    fun loginIn(username: String, password: String): Boolean{

        val loginSuccess = usuarioRepository.login(username, password)
        var success = false
        if (loginSuccess) {
            val user = usuarioRepository.findUsuarioByUsername(username)

            if(user != null){
                // Guardar la información en SharedPreferences
                val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putString("username", username)
                    putString("role", user.rol)
                    putBoolean("isLogged", true)
                    apply()
                    success = true
                }
            }
        }
        return success
    }
}