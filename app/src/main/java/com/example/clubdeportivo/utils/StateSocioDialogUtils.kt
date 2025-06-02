package com.example.clubdeportivo.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.clubdeportivo.controllers.SocioController
import com.example.clubdeportivo.entities.dto.SocioExpirationDayDto

object StateSocioDialogUtils {

    fun showDialogSocioState(
        context: Context,
        socio: SocioExpirationDayDto,
    ) {
        AlertDialog.Builder(context).apply {
            setTitle("Estado de la inscripción")
            setMessage("Estado actual: ${if (socio.state) "Activo" else "Inactivo"}")

            setNegativeButton("Aceptar", null)
            show()
        }
    }

    fun showDialogSuspendedState(
        context: Context,
        socio: SocioExpirationDayDto,
        position: Int,
        socios: MutableList<SocioExpirationDayDto>,
        notifyItemChanged: (Int) -> Unit,
        socioController: SocioController
    ) {
        AlertDialog.Builder(context).apply {
            setTitle("Estado de la inscripción")
            setMessage("Estado actual del Socio: ${if (socio.state) "Activo" else "Inactivo"}")

            val actionText = if (socio.state) "Suspender" else "Rehabilitar"
            setPositiveButton(actionText) { _, _ ->
                val newState = !socio.state
                socioController.updateState(socio.id, newState)

                socios[position] = socio.copy(state = newState)
                notifyItemChanged(position)
            }
            setNegativeButton("Cancelar", null)
            show()
        }
    }
}