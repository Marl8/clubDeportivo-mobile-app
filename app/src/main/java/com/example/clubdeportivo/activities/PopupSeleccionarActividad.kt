package com.example.clubdeportivo.activities

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R

class PopupSeleccionarActividad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_popup_seleccionar_actividad)

        setupUI()
    }

    private fun setupUI(){
        supportActionBar?.hide()

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val withDisplay = displayMetrics.widthPixels
        val heightDisplay = displayMetrics.heightPixels

        window.setLayout((withDisplay * 0.8).toInt(), (heightDisplay * 0.6).toInt())

        this.setFinishOnTouchOutside(true)
    }
}