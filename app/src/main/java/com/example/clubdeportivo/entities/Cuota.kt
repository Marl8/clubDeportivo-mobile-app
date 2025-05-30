package com.example.clubdeportivo.entities

import org.threeten.bp.LocalDate

data class Cuota(
    val idCuota: Int? = null,
    val valueCuota: Double,
    val payDay: LocalDate,
    val expirationDate: LocalDate,
    val nextExpirationDate: LocalDate,
    val paymentMethod: String,
    val numberCuotas: Int,
    val state: Boolean
)
