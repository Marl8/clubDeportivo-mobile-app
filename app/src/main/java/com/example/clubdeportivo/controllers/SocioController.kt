package com.example.clubdeportivo.controllers

import com.example.clubdeportivo.entities.Socio
import com.example.clubdeportivo.entities.dto.SocioExpirationDayDto
import com.example.clubdeportivo.repositories.SocioRepository
import org.threeten.bp.LocalDate

class SocioController(private val socioRepository: SocioRepository) {

    fun enrollSocio(socio: Socio): Boolean{
        val exist = isExist(socio)
        var result = false
        if(!exist){
            val saveSocio: Boolean = socioRepository.saveSocio(socio)
            if (saveSocio) result = true
        }
        return result
    }

    private fun isExist(socio: Socio): Boolean{
        return socioRepository.existSocio(socio.dni)
    }

    fun getSocio(dni: String): Socio?{
        val socio: Socio? = socioRepository.findSocioByDni(dni)
        return socio
    }

    fun updateSocio(socio: Socio): Boolean{
        return socioRepository.updateSocio(socio)
    }

    fun listSociosByExpiationDay(date: LocalDate): MutableList<SocioExpirationDayDto>{
        return socioRepository.listSocioByExpirationDay(date)
    }

    fun listSociosMora(date: LocalDate): MutableList<SocioExpirationDayDto>{
        return socioRepository.listSociosMora(date)
    }

    fun updateState(idSocio: Int, newState: Boolean): Boolean {
        return socioRepository.updateState(idSocio, newState)
    }
}