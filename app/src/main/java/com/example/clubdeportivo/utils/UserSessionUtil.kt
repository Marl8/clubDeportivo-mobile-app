package com.example.clubdeportivo.utils

import android.content.Context

object UserSessionUtil {

    fun getUserSession(context: Context): String {
        return context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .getString("username", "") ?: ""
    }

    fun getUserRole(context: Context): String {
        return context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .getString("role", "Empleado") ?: "Empleado"
    }

}