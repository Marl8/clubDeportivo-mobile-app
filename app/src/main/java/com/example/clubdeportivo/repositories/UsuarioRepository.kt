package com.example.clubdeportivo.repositories

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.clubdeportivo.entities.dto.UsuarioDto
import com.example.clubdeportivo.helpers.DataBaseHelper
import com.example.clubdeportivo.utils.SecurityUtils

class UsuarioRepository(private val context: Context) {

    private val dbHelper = DataBaseHelper(context)

    fun login(username: String, password: String): Boolean {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val hashedPassword = SecurityUtils.sha256(password)

        val query = "SELECT * FROM usuarios WHERE username = ? AND password = ?"
        val cursor = db.rawQuery(query, arrayOf(username, hashedPassword))

        val isLogged = cursor.count > 0
        cursor.close()
        db.close()
        return isLogged
    }

    fun findUsuarioByUsername(username: String): UsuarioDto?{
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val query: String = "SELECT us.id, us.nombre AS nombre_user, us.apellido, us.dni, r.Nombre AS nombre_rol " +
                "FROM usuarios As us " +
                "INNER JOIN roles As r ON us.fk_rol = r.id_rol " +
                " WHERE username = ?"
        val cursor = db.rawQuery(query, arrayOf(username))

        var user: UsuarioDto? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("nombre_user"))
            val lastName = cursor.getString(cursor.getColumnIndexOrThrow("apellido"))
            val dni = cursor.getString(cursor.getColumnIndexOrThrow("dni"))
            val rolName = cursor.getString(cursor.getColumnIndexOrThrow("nombre_rol"))

            user = UsuarioDto(id, name, lastName, dni, rolName)
        }
        cursor.close()
        return user
    }
}