package com.example.clubdeportivo.activities

import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.example.clubdeportivo.R

class PopupSeleccionarActividad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_popup_seleccionar_actividad)

        setupUI()
        setupActivitiesRadioGroup()
    }

    private fun setupUI(){
        supportActionBar?.hide()

        val displayMetrics = DisplayMetrics()
        /* Deprecado a partir de Android 11 (API30).
            Recomendado usar WindowMetric. Pero nosotros usamos API24.
          -----------------  Ignorar del deprecado. --------------*/
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val withDisplay = displayMetrics.widthPixels
        val heightDisplay = displayMetrics.heightPixels

        window.setLayout((withDisplay * 0.8).toInt(), (heightDisplay * 0.6).toInt())

        this.setFinishOnTouchOutside(true)
    }

    private fun setupActivitiesRadioGroup(){
        val activities = resources.getStringArray(R.array.activities)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupActivities)

        for (activity in activities){
            val radioButton = RadioButton(this).apply {
                text = activity
                layoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0,8,0,8)
                }
                setPadding(16,16,16,16)
                buttonTintList = ContextCompat.getColorStateList(context, R.color.radio_button_selector )
            }
            radioGroup.addView(radioButton)
        }
    }
}