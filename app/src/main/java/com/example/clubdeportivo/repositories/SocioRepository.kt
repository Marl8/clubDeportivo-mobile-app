package com.example.clubdeportivo.repositories

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.clubdeportivo.entities.Socio
import com.example.clubdeportivo.entities.dto.SocioExpirationDayDto
import com.example.clubdeportivo.helpers.DataBaseHelper
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class SocioRepository(context: Context) {

    private val dbHelper = DataBaseHelper(context)

    fun saveSocio(socio: Socio): Boolean {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put("nombre", socio.name)
            put("apellido", socio.lastName)
            put("dni", socio.dni)
            put("email", socio.email)
            put("telefono", socio.phone)
            put("apto_fisico", socio.aptoFisico)
            put("estado", socio.stateSocio)
        }
        val result = db.insert("socios", null, contentValues)
        db.close()
        return result != -1L // SqlLite devuelve un -1 si falla
    }

    fun existSocio(dni: String): Boolean {
        val db: SQLiteDatabase = dbHelper.readableDatabase

        val query = "SELECT * FROM socios WHERE dni = ?"
        val cursor = db.rawQuery(query, arrayOf(dni))

        val exist = cursor.count > 0
        cursor.close()
        db.close()
        return exist
    }

    fun findSocioByDni(dni: String): Socio? {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val query: String = "SELECT * " +
                "FROM socios WHERE dni = ?"
        val cursor = db.rawQuery(query, arrayOf(dni))

        var socio: Socio? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id_socio"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val lastName = cursor.getString(cursor.getColumnIndexOrThrow("apellido"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow("telefono"))
            val dniSocio = cursor.getString(cursor.getColumnIndexOrThrow("dni"))
            val state = cursor.getInt(cursor.getColumnIndexOrThrow("estado")) != 0
            val aptoFisico = cursor.getInt(cursor.getColumnIndexOrThrow("apto_fisico")) != 0

            socio = Socio(id, state, aptoFisico, name, lastName, dniSocio, email, phone)
        }
        cursor.close()
        return socio
    }

    fun updateSocio(socio: Socio): Boolean{
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put("nombre", socio.name)
            put("apellido", socio.lastName)
            put("dni", socio.dni)
            put("email", socio.email)
            put("telefono", socio.phone)
            put("apto_fisico", socio.aptoFisico)
            put("estado", socio.stateSocio)
        }
        val whereClause = "id_Socio = ?"
        val args = arrayOf(socio.idSocio.toString())

        val rowsAffected = db.update("socios", contentValues, whereClause, args)
        db.close()
        return rowsAffected > 0
    }

    fun listSocioByExpirationDay(date: LocalDate): MutableList<SocioExpirationDayDto> {
        val db: SQLiteDatabase = dbHelper.readableDatabase

        val query: String =  "SELECT s.id_socio, s.nombre, s.apellido, s.dni, c.fecha_prox_vencimiento, c.estado " +
                "FROM socios AS s INNER JOIN cuotas AS c ON s.id_socio = c.fk_socio " +
                "WHERE date(c.fecha_prox_vencimiento) = ?"

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = date.format(formatter)

        val cursor = db.rawQuery(query, arrayOf(formattedDate))

        val socios = mutableListOf<SocioExpirationDayDto>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id_socio"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val lastName = cursor.getString(cursor.getColumnIndexOrThrow("apellido"))
                val dniSocio = cursor.getString(cursor.getColumnIndexOrThrow("dni"))
                val state = cursor.getInt(cursor.getColumnIndexOrThrow("estado")) != 0
                val expirationDayStr = cursor.getString(cursor.getColumnIndexOrThrow("fecha_prox_vencimiento"))
                val expirationDay = LocalDate.parse(expirationDayStr, formatter)

                val socio = SocioExpirationDayDto(id, name, lastName, dniSocio, state, expirationDay)
                socios.add(socio)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return socios
    }

    fun listSociosMora(date: LocalDate): MutableList<SocioExpirationDayDto> {
        val db: SQLiteDatabase = dbHelper.readableDatabase

        val query: String = "SELECT s.id_socio, s.nombre, s.apellido, s.dni, c.fecha_prox_vencimiento, s.estado " +
                "FROM socios AS s " +
                "INNER JOIN cuotas c ON s.id_socio = c.fk_socio " +
                "INNER JOIN (SELECT fk_socio, MAX(fecha_prox_vencimiento) AS max_fecha FROM cuotas GROUP BY fk_socio) AS px " +
                "ON c.fk_socio = px.fk_socio AND c.fecha_prox_vencimiento = px.max_fecha " +
                "WHERE date(c.fecha_prox_vencimiento) < ? " +
                "ORDER BY c.fecha_prox_vencimiento DESC;"

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = date.format(formatter)

        val cursor = db.rawQuery(query, arrayOf(formattedDate))

        val socios = mutableListOf<SocioExpirationDayDto>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id_socio"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val lastName = cursor.getString(cursor.getColumnIndexOrThrow("apellido"))
                val dniSocio = cursor.getString(cursor.getColumnIndexOrThrow("dni"))
                val state = cursor.getInt(cursor.getColumnIndexOrThrow("estado")) != 0
                val expirationDayStr = cursor.getString(cursor.getColumnIndexOrThrow("fecha_prox_vencimiento"))
                val expirationDay = LocalDate.parse(expirationDayStr, formatter)

                val socio = SocioExpirationDayDto(id, name, lastName, dniSocio, state, expirationDay)
                socios.add(socio)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return socios
    }

    fun updateState(idSocio: Int?, newState: Boolean): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("estado", if (newState) 1 else 0)
        }
        val rowsAffected = db.update("socios", values, "id_socio = ?", arrayOf(idSocio.toString()))
        return rowsAffected > 0
    }
}