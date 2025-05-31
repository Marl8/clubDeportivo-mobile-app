package com.example.clubdeportivo.repositories

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.clubdeportivo.entities.Actividad
import com.example.clubdeportivo.helpers.DataBaseHelper

class ActividadRepository(context: Context) {

    private val dbHelper = DataBaseHelper(context)

    fun enrollSocioActividad(actividadId: Int?, state:Boolean, idSocio: Int?): Boolean {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put("id_act", actividadId)
            put("id_socio", idSocio)
            put("estado", state)
        }
        val result = db.insert("act_socios", null, contentValues)
        db.close()
        return result != -1L
    }

    fun findActividadByName(name: String): Actividad? {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val query: String = "SELECT * " +
                "FROM actividades WHERE nombre = ?"
        val cursor = db.rawQuery(query, arrayOf(name))

        var actividad: Actividad? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id_actividad"))
            val nameAct = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val value = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"))
            val maxQuotaSocio = cursor.getInt(cursor.getColumnIndexOrThrow("max_cupo_socio"))
            val maxQuotaNoSocio = cursor.getInt(cursor.getColumnIndexOrThrow("max_cupo_no_socio"))
            val quotaSocioAvailable = cursor.getInt(cursor.getColumnIndexOrThrow("cupo_socio_disponible"))
            val quotaNoSocioAvailable = cursor.getInt(cursor.getColumnIndexOrThrow("cupo_no_socio_disponible"))

            actividad = Actividad(id, nameAct, value, maxQuotaSocio, maxQuotaNoSocio, quotaSocioAvailable, quotaNoSocioAvailable)
        }
        cursor.close()
        return actividad
    }

    fun updateActividad(actividad: Actividad): Boolean {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put("nombre", actividad.name)
            put("valor", actividad.value)
            put("max_cupo_socio", actividad.maxQuotaSocio)
            put("max_cupo_no_socio", actividad.maxQuotaNoSocio)
            put("cupo_socio_disponible", actividad.quotaSocioAvailable)
            put("cupo_no_socio_disponible", actividad.quotaNoSocioAvailable)
        }
        val whereClause = "id_actividad = ?"
        val args = arrayOf(actividad.id.toString())

        // Ejecutar la actualización
        val rowsAffected = db.update("actividades", contentValues, whereClause, args)
        db.close()
        return rowsAffected > 0  // Si se actualizó al menos una fila, retorna true
    }

    fun isSocioAlreadyEnrolled(actividadId: Int?, idSocio: Int?): Boolean {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM act_socios WHERE id_act = ? AND id_socio = ?"
        val cursor = db.rawQuery(query, arrayOf(actividadId.toString(), idSocio.toString()))
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }
}