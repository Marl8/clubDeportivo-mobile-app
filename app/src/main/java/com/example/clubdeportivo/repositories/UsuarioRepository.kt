package com.example.clubdeportivo.repositories

import android.content.Context
import android.database.sqlite.SQLiteDatabase
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
}