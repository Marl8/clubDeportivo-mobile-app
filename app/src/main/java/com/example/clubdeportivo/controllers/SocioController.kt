package com.example.clubdeportivo.controllers

import com.example.clubdeportivo.entities.Socio
import com.example.clubdeportivo.repositories.SocioRepository

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
}