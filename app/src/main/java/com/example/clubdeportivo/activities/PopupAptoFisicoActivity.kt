package com.example.clubdeportivo.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowInsets
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clubdeportivo.R
import com.google.android.material.button.MaterialButton
import androidx.core.graphics.drawable.toDrawable

class PopupAptoFisicoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_popup_apto_fisico)

        window.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        setUI()

        val btnConfirm: MaterialButton = findViewById(R.id.confirm)
        val btnCancel: MaterialButton = findViewById(R.id.cancel)

        btnConfirm.setOnClickListener {
            val intent = Intent(this, InscribirSocioActivity::class.java)
            intent.putExtra("aptoFisico", true)
            startActivity(intent)
            finish()
        }

        btnCancel.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUI(){
        supportActionBar?.hide()

        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            val width = windowMetrics.bounds.width() - insets.left - insets.right
            val height = windowMetrics.bounds.height() - insets.top - insets.bottom

            window.setLayout((width * 0.8).toInt(), (height * 0.65).toInt())
        } else {
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = displayMetrics.widthPixels
            val height = displayMetrics.heightPixels

            window.setLayout((width * 0.8).toInt(), (height * 0.65).toInt())
        }

        this.setFinishOnTouchOutside(true)
    }
}