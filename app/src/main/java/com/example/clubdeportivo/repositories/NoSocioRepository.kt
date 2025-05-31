package com.example.clubdeportivo.repositories

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.clubdeportivo.entities.NoSocio
import com.example.clubdeportivo.helpers.DataBaseHelper

class NoSocioRepository(context: Context) {

    private val dbHelper = DataBaseHelper(context)

    fun saveNoSocio(noSocio: NoSocio): Boolean {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put("nombre", noSocio.name)
            put("apellido", noSocio.lastName)
            put("dni", noSocio.dni)
            put("email", noSocio.email)
            put("telefono", noSocio.phone)
            put("apto_fisico", noSocio.aptoFisico)
        }
        val result = db.insert("noSocios", null, contentValues)
        db.close()
        return result != -1L
    }

    fun existNoSocio(dni: String): Boolean {
        val db: SQLiteDatabase = dbHelper.readableDatabase

        val query = "SELECT * FROM noSocios WHERE dni = ?"
        val cursor = db.rawQuery(query, arrayOf(dni))

        val exist = cursor.count > 0
        cursor.close()
        db.close()
        return exist
    }

    fun findNoSocioByDni(dni: String): NoSocio? {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val query: String = "SELECT * " +
                "FROM noSocios WHERE dni = ?"
        val cursor = db.rawQuery(query, arrayOf(dni))

        var noSocio: NoSocio? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id_noSocio"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val lastName = cursor.getString(cursor.getColumnIndexOrThrow("apellido"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow("telefono"))
            val dniNoSocio = cursor.getString(cursor.getColumnIndexOrThrow("dni"))
            val aptoFisico = cursor.getInt(cursor.getColumnIndexOrThrow("apto_fisico")) != 0

            noSocio = NoSocio(id, aptoFisico, name, lastName, dniNoSocio, email, phone)
        }
        cursor.close()
        return noSocio
    }
}