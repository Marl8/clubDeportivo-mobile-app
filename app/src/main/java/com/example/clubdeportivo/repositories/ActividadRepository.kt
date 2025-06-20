package com.example.clubdeportivo.repositories

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.clubdeportivo.entities.Actividad
import com.example.clubdeportivo.helpers.DataBaseHelper
import org.threeten.bp.LocalDate

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

    fun enrollNoSocioActividad(actividadId: Int?, date: LocalDate?, amount: Double?, idNoSocio: Int?): Boolean {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put("id_act", actividadId)
            put("id_noSocio", idNoSocio)
            put("dia_habilitado", date.toString())
            put("monto_pagado", amount)
        }
        val result = db.insert("act_nosocios", null, contentValues)
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

    fun getAllActividades(): List<Actividad>{
        val db: SQLiteDatabase = dbHelper.readableDatabase

        val query= "SELECT * FROM actividades"
        val cursor = db.rawQuery(query, arrayOf())

        val actividades = mutableListOf<Actividad>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id_actividad"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val value = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"))
                val maxQuotaSocio = cursor.getInt(cursor.getColumnIndexOrThrow("max_cupo_socio"))
                val maxQuotaNoSocio = cursor.getInt(cursor.getColumnIndexOrThrow("max_cupo_no_socio"))
                val quotaSocioAvailable = cursor.getInt(cursor.getColumnIndexOrThrow("cupo_socio_disponible"))
                val quotaNoSocioAvailable = cursor.getInt(cursor.getColumnIndexOrThrow("cupo_no_socio_disponible"))

                val actividad = Actividad(id, name, value, maxQuotaSocio, maxQuotaNoSocio, quotaSocioAvailable, quotaNoSocioAvailable)
                actividades.add(actividad)
            } while (cursor.moveToNext())
        }
            cursor.close()
            return actividades
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

    fun isNoSocioAlreadyEnrolled(actividadId: Int?, idNoSocio: Int?): Boolean {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM act_nosocios WHERE id_act = ? AND id_noSocio = ?"
        val cursor = db.rawQuery(query, arrayOf(actividadId.toString(), idNoSocio.toString()))
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }

    fun paymentDairy(actividadId: Int, noSocioId: Int?, date: LocalDate, amount: Double): Boolean {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put("id_act", actividadId)
            put("id_noSocio", noSocioId)
            put("dia_habilitado", date.toString())
            put("monto_pagado", amount)
        }
        val whereClause = "id_act = ? and id_noSocio = ?"
        val args = arrayOf(actividadId.toString(), noSocioId.toString())

        val rowsAffected = db.update("act_nosocios", contentValues, whereClause, args)
        db.close()
        return rowsAffected > 0  // Si se actualizó al menos una fila, retorna true
    }

    fun findNoSocioQuotaAvailable(actividadId: Int, date: LocalDate): Int{
        val db = dbHelper.readableDatabase
        val query = "SELECT count(*) FROM act_nosocios WHERE id_act = ? AND dia_habilitado = ?"
        val cursor = db.rawQuery(query, arrayOf(actividadId.toString(), date.toString()))
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)  // Devuelve el valor de la columna 0
        }
        cursor.close()
        return count
    }

    fun createActividad(act: Actividad): Boolean{
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put("nombre", act.name)
            put("valor", act.value)
            put("max_cupo_socio", act.maxQuotaSocio)
            put("max_cupo_no_socio", act.maxQuotaNoSocio)
            put("cupo_socio_disponible", act.quotaSocioAvailable)
            put("cupo_no_socio_disponible", act.quotaNoSocioAvailable)
        }

        val result = db.insert("actividades", null, contentValues)
        db.close()
        return result != -1L
    }
}