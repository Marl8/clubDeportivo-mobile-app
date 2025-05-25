package com.example.clubdeportivo.repositories

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.clubdeportivo.entities.Socio
import com.example.clubdeportivo.helpers.DataBaseHelper

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
}