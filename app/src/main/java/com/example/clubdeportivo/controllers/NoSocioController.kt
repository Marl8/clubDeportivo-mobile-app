package com.example.clubdeportivo.controllers

import com.example.clubdeportivo.entities.NoSocio
import com.example.clubdeportivo.entities.dto.NoSocioEnabledDto
import com.example.clubdeportivo.repositories.NoSocioRepository
import com.example.clubdeportivo.repositories.SocioRepository
import org.threeten.bp.LocalDate

class NoSocioController(private val noSocioRepository: NoSocioRepository,
                        private val socioRepository: SocioRepository
) {

    fun enrollNoSocio(noSocio: NoSocio): Pair<Boolean, String>{
        var result = false
        val message: String
        val existSocio = socioRepository.existSocio(noSocio.dni)
        val existNoSocio = noSocioRepository.existNoSocio(noSocio.dni)
        if(!existSocio && !existNoSocio){
            val saveSocio: Boolean = noSocioRepository.saveNoSocio(noSocio)
            if (saveSocio){
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

    fun getNoSocio(dni: String): NoSocio?{
        val noSocio: NoSocio? = noSocioRepository.findNoSocioByDni(dni)
        return noSocio
    }

    fun getListNoSociosDayEnabled(date: LocalDate): List<NoSocioEnabledDto>{
        return noSocioRepository.listNoSocioEnabled(date)
    }
}