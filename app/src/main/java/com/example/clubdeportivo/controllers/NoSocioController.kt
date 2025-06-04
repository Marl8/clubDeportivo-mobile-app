package com.example.clubdeportivo.controllers

import com.example.clubdeportivo.entities.NoSocio
import com.example.clubdeportivo.entities.dto.NoSocioEnabledDto
import com.example.clubdeportivo.repositories.NoSocioRepository
import org.threeten.bp.LocalDate

class NoSocioController(private val noSocioRepository: NoSocioRepository) {

    fun enrollNoSocio(noSocio: NoSocio): Boolean{
        val exist = isExist(noSocio)
        var result = false
        if(!exist){
            val saveSocio: Boolean = noSocioRepository.saveNoSocio(noSocio)
            if (saveSocio) result = true
        }
        return result
    }

    private fun isExist(noSocio: NoSocio): Boolean{
        return noSocioRepository.existNoSocio(noSocio.dni)
    }

    fun getNoSocio(dni: String): NoSocio?{
        val noSocio: NoSocio? = noSocioRepository.findNoSocioByDni(dni)
        return noSocio
    }

    fun getListNoSociosDayEnabled(date: LocalDate): List<NoSocioEnabledDto>{
        return noSocioRepository.listNoSocioEnabled(date)
    }
}