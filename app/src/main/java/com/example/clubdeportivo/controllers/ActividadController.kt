package com.example.clubdeportivo.controllers

import com.example.clubdeportivo.entities.Actividad
import com.example.clubdeportivo.entities.NoSocio
import com.example.clubdeportivo.entities.Socio
import com.example.clubdeportivo.repositories.ActividadRepository
import com.example.clubdeportivo.repositories.NoSocioRepository
import com.example.clubdeportivo.repositories.SocioRepository

class ActividadController(private val actividadRepository: ActividadRepository,
                          private val socioRepository: SocioRepository,
                          private val noSocioRepository: NoSocioRepository) {

    fun enrollSocioActividad(nameActividad: String, dniSocio: String): Pair<Boolean, String> {
        var success = false
        val message: String

        val socio: Socio? = socioRepository.findSocioByDni(dniSocio)
        if (socio == null) {
            message = "El socio con DNI $dniSocio no existe."
        } else {
            val actividad: Actividad? = actividadRepository.findActividadByName(nameActividad)
            if (actividad == null) {
                message = "La actividad '$nameActividad' no existe."
            } else {
                val isEnroll: Boolean = actividadRepository.isSocioAlreadyEnrolled(actividad.id, socio.idSocio)
                if (isEnroll) {
                    message = "El socio ya está inscripto en esta actividad."
                } else if (actividad.quotaSocioAvailable <= 0) {
                    message = "No hay cupo disponible para socios en esta actividad."
                } else {
                    val state = true
                    success = actividadRepository.enrollSocioActividad(actividad.id, state, socio.idSocio)
                     if (success) {
                         actividad.quotaSocioAvailable -= 1
                         actividadRepository.updateActividad(actividad)
                         message = "Inscripción realizada con éxito."
                    } else {
                         message = "Error al actualizar la actividad."
                    }
                }
            }
        }
        return Pair(success, message)
    }

    fun enrollNoSocioActividad(nameActividad: String, dniNoSocio: String): Pair<Boolean, String> {
        var success = false
        val message: String

        val noSocio: NoSocio? = noSocioRepository.findNoSocioByDni(dniNoSocio)
        if (noSocio == null) {
            message = "El no socio con DNI $dniNoSocio no existe."
        } else {
            val actividad: Actividad? = actividadRepository.findActividadByName(nameActividad)
            if (actividad == null) {
                message = "La actividad '$nameActividad' no existe."
            } else {
                val isEnroll: Boolean = actividadRepository.isNoSocioAlreadyEnrolled(actividad.id, noSocio.idNoSocio)
                if (isEnroll) {
                    message = "El no socio ya está inscripto en esta actividad."
                } else if (actividad.quotaNoSocioAvailable <= 0) {
                    message = "No hay cupo disponible para no socios en esta actividad."
                } else {
                    success = actividadRepository.enrollNoSocioActividad(actividad.id, null, null, noSocio.idNoSocio)
                    if (success) {
                        actividad.quotaNoSocioAvailable -= 1
                        actividadRepository.updateActividad(actividad)
                        message = "Inscripción realizada con éxito."
                    } else {
                        message = "Error al actualizar la actividad."
                    }
                }
            }
        }
        return Pair(success, message)
    }
}