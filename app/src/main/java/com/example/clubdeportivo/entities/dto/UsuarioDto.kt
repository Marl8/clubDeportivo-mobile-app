package com.example.clubdeportivo.entities.dto

data class UsuarioDto(
    val idUsuario: Int?,
    val username: String,
    val password: String,
    val fkRol: Int,
    val name: String,
    val lastName: String,
    val dni: String,
    val email: String,
    val phone: String
)

