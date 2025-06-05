package com.example.clubdeportivo.repositories

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.clubdeportivo.entities.Usuario
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

    fun findUsuarioByUsername(username: String): Usuario?{
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val query: String = "SELECT us.id, us.nombre AS nombre_user, us.apellido, us.dni, us.email, " +
                "us.telefono, us.username, us.password, r.Nombre AS nombre_rol " +
                "FROM usuarios As us " +
                "INNER JOIN roles As r ON us.fk_rol = r.id_rol " +
                " WHERE username = ?"
        val cursor = db.rawQuery(query, arrayOf(username))

        var user: Usuario? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("nombre_user"))
            val lastName = cursor.getString(cursor.getColumnIndexOrThrow("apellido"))
            val dni = cursor.getString(cursor.getColumnIndexOrThrow("dni"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow("telefono"))
            val nick = cursor.getString(cursor.getColumnIndexOrThrow("username"))
            val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
            val rolName = cursor.getString(cursor.getColumnIndexOrThrow("nombre_rol"))

            user = Usuario(id, nick, password, rolName, name, lastName, dni, email, phone)
        }
        cursor.close()
        return user
    }

    fun updateUsuario(usuario: UsuarioDto): Boolean{
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put("nombre", usuario.name)
            put("apellido", usuario.lastName)
            put("dni", usuario.dni)
            put("email", usuario.email)
            put("telefono", usuario.phone)
            put("username", usuario.username)
            put("password", usuario.password)
            put("fk_rol", usuario.fkRol)
        }
        val whereClause = "id = ?"
        val args = arrayOf(usuario.idUsuario.toString())

        val rowsAffected = db.update("usuarios", contentValues, whereClause, args)
        db.close()
        return rowsAffected > 0
    }
}