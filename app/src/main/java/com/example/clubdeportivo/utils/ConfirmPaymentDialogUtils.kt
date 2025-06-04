package com.example.clubdeportivo.utils

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.entities.NoSocio

object ConfirmPaymentDialogUtils {

    fun showDairyPaymentDialog(
        activity: AppCompatActivity,
        noSocio: NoSocio?,
        actName: String,
        amount: Double,
        onConfirmed: (Boolean) -> Unit
    ) {
        AlertDialog.Builder(activity)
            .setTitle("Confirme Datos")
            .setMessage("Nombre del No Socio: ${noSocio?.name} ${noSocio?.lastName}\n\n" +
                    "Nombre actividad: $actName\n\n" +
                    "Monto: $amount")
            .setPositiveButton("Sí") { _, _ ->
                onConfirmed(true)
            }
            .setNegativeButton("No") { _, _ ->
                onConfirmed(false)
            }
            .create()
            .show()
    }

    fun showEnrollActividadDialog(
        activity: AppCompatActivity,
        name: String,
        actName: String,
        onConfirmed: (Boolean) -> Unit
    ) {
        AlertDialog.Builder(activity)
            .setTitle("Confirme Datos")
            .setMessage("Nombre del Cliente: ${name}\n\n" +
                    "Nombre actividad: $actName\n\n")
            .setPositiveButton("Sí") { _, _ ->
                onConfirmed(true)
            }
            .setNegativeButton("No") { _, _ ->
                onConfirmed(false)
            }
            .create()
            .show()
    }
}