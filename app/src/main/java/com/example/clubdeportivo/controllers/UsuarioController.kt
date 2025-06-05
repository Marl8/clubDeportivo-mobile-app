package com.example.clubdeportivo.controllers

import com.example.clubdeportivo.entities.Usuario
import com.example.clubdeportivo.entities.dto.UsuarioDto
import com.example.clubdeportivo.repositories.UsuarioRepository

class UsuarioController(private val usuarioRepository: UsuarioRepository) {

    fun updateUsuario(user: Usuario): Boolean{
        var fk = 0

        if(user.rol == "Administrador"){
            fk = 1
        }else if (user.rol == "Empleado"){
            fk = 2
        }
        val userEdit = UsuarioDto(user.idUsuario, user.username, user.password, fk, user.name, user.lastName,
            user.dni, user.email, user.phone)
        return usuarioRepository.updateUsuario(userEdit)
    }

    fun findUsuarioByUsername(username: String): Usuario?{
        return usuarioRepository.findUsuarioByUsername(username)
    }
}