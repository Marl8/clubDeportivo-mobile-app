package com.example.clubdeportivo.entities.dto

import org.threeten.bp.LocalDate

data class NoSocioEnabledDto(
    val id: Int,
    val nameNoSocio: String,
    val lastName: String,
    val dni: String,
    val nameAct: String,
    val enableDay: LocalDate
)
