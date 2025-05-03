package com.example.clubdeportivo.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent
import android.widget.ImageButton;
import com.example.clubdeportivo.activities.LoginActivity

fun setupLogoutButton(activity: Activity, button: ImageButton){
    button.setOnClickListener {
        AlertDialog.Builder(activity)
            .setTitle("Cerrar sesión")
            .setMessage("¿Estás seguro de que deseas salir?")
            .setPositiveButton("Sí") { _, _ ->
            // Borrar sesión
            val prefs = activity.getSharedPreferences("MyPrefs", Activity.MODE_PRIVATE)
            prefs.edit().clear().apply()

            // Redirigir al login
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
