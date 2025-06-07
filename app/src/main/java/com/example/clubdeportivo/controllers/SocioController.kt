package com.example.clubdeportivo.controllers

import com.example.clubdeportivo.entities.Socio
import com.example.clubdeportivo.entities.dto.SocioExpirationDayDto
import com.example.clubdeportivo.repositories.NoSocioRepository
import com.example.clubdeportivo.repositories.SocioRepository
import org.threeten.bp.LocalDate

class SocioController(private val socioRepository: SocioRepository,
                      private val noSocioRepository: NoSocioRepository) {

    fun enrollSocio(socio: Socio): Pair<Boolean, String>{
        var result = false
        val message: String
        val existSocio = socioRepository.existSocio(socio.dni)
        val existNoSocio = noSocioRepository.existNoSocio(socio.dni)

        if(!existSocio && !existNoSocio){
            val saveSocio: Boolean = socioRepository.saveSocio(socio)
            if (saveSocio) {
                result = true
                message = "Inscripción exitosa"
            }
            else{
                message = "Error no ha podido completarse la operación."
            }
        }else if(existSocio){
            message = "El cliente ya se encuentra registrado como socio."
        }else{
            message = "El cliente ya se encuentra registrado como no socio."
        }
        return Pair(result, message)
    }

    fun getSocio(dni: String): Socio?{
        val socio: Socio? = socioRepository.findSocioByDni(dni)
        return socio
    }

    fun getListSociosByExpiationDay(date: LocalDate): MutableList<SocioExpirationDayDto>{
        return socioRepository.listSocioByExpirationDay(date)
    }

    fun getListSociosMora(date: LocalDate): MutableList<SocioExpirationDayDto>{
        return socioRepository.listSociosMora(date)
    }

    fun updateState(idSocio: Int?, newState: Boolean): Boolean {
        return socioRepository.updateState(idSocio, newState)
    }
}