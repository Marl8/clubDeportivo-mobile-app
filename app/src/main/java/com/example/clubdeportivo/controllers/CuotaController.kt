package com.example.clubdeportivo.controllers

import com.example.clubdeportivo.entities.Cuota
import com.example.clubdeportivo.entities.Socio
import com.example.clubdeportivo.repositories.CuotaRepository
import com.example.clubdeportivo.repositories.SocioRepository
import org.threeten.bp.LocalDate

class CuotaController(private val cuotaRepository: CuotaRepository,
                      private val socioRepository: SocioRepository) {


    fun payCuota(paymentMethod: String, numberCuotas: Int, valueCuota: Double, dni: String): Boolean{
        var success = false
        val socio: Socio? = socioRepository.findSocioByDni(dni)
        if(socio != null){
            val payday: LocalDate = LocalDate.now()
            val existCuota: Boolean = cuotaRepository.existCuotaSocio(socio.idSocio)

            val expirationDate: LocalDate = if (existCuota) {
                cuotaRepository.findExpirationDate(socio.idSocio)
                    ?: throw IllegalStateException("Error al intentar recuperar la fecha de vencimiento.")
            } else {
                LocalDate.now()
            }
            val nextExpirationDate: LocalDate = expirationDate.plusDays(30)
            val state = true
            val cuota = Cuota(null, valueCuota, payday, expirationDate,
                nextExpirationDate, paymentMethod, numberCuotas, state)
            success = cuotaRepository.saveCuota(cuota, socio.idSocio)
        }
        return success
    }
}