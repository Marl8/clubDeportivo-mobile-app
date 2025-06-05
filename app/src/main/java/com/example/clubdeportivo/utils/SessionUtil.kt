package com.example.clubdeportivo.utils

import android.app.Activity
import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import com.example.clubdeportivo.R
import com.example.clubdeportivo.activities.LoginActivity
import com.example.clubdeportivo.activities.MenuActivity

fun setupLogoutButton(activity: Activity, button: ImageButton){
    button.setOnClickListener {
        //showAlert
        AlertDialog.Builder(activity)
            .setTitle("Cerrar sesión")
            .setMessage("¿Estás seguro de que deseas salir?")
            .setPositiveButton("Sí") { _, _ ->
            // Borrar sesión
            val prefs = activity.getSharedPreferences("user_prefs", Activity.MODE_PRIVATE)
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
fun backRedirect(activity: Activity, btnBack:ImageButton){
    btnBack.setOnClickListener {
        val intent = Intent(activity, MenuActivity::class.java)

        // Limpiar la pila de actividades
        //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

        val options = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_right, R.anim.slide_out_left)
        activity.startActivity(intent, options.toBundle())

        //cerrar esta activity después de que la animación termine
        Handler(Looper.getMainLooper()).postDelayed({
            activity.finish() // cierra la actividad actual
        }, 800)
    }
}
