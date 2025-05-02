package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import com.example.clubdeportivo.activities.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Espera 2 segundos y pasa a LoginActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            // Cierra el Splash para que no vuelva al apretar atrás
            finish()
        }, 2000) // 2000 milisegundos = 2 segundos
    }
}