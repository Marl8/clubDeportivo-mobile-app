package com.example.clubdeportivo.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.clubdeportivo.R
import com.example.clubdeportivo.controllers.ActividadController
import com.example.clubdeportivo.repositories.ActividadRepository
import com.example.clubdeportivo.repositories.NoSocioRepository
import com.example.clubdeportivo.repositories.SocioRepository
import com.example.clubdeportivo.utils.ModalStyleUtils

class PopupSeleccionarActividad : AppCompatActivity() {

    private var optionSelect: String = ""
    private lateinit var actividadController: ActividadController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_popup_seleccionar_actividad)

        ModalStyleUtils.setUI(this)

        actividadController = ActividadController(
            ActividadRepository(this),
            SocioRepository(this), NoSocioRepository(this)
        )
        setupActivitiesRadioGroup()

        optionSelect = intent.getStringExtra("option").orEmpty()
        val btnSelectActivity: Button = findViewById(R.id.btnSelectActivity)

        btnSelectActivity.setOnClickListener {
            if (optionSelect.isNotEmpty()) {
            val resultIntent = Intent()
            resultIntent.putExtra("selected_activity", optionSelect)
            setResult(RESULT_OK, resultIntent)
            finish()
            } else {
                Toast.makeText(this, "Por favor selecciona una actividad", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupActivitiesRadioGroup(){
        //val activities = resources.getStringArray(R.array.activities)
        val activities = actividadController.getallActividades()
        val radioGroup: RadioGroup = findViewById(R.id.radioGroupActivities)

        // Crear RadioButtons con íds
        for (activity in activities) {
            val activityId = activity.id ?: continue  // salta si es null
            val radioButton = RadioButton(this).apply {
                id = activity.id
                text = activity.name
                layoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 8, 0, 8)
                }
                setPadding(16, 16, 16, 16)
                buttonTintList = ContextCompat.getColorStateList(context, R.color.radio_button_selector)
                setTextColor(Color.WHITE)
            }
            radioGroup.addView(radioButton)
        }
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton: RadioButton? = group.findViewById(checkedId)
            selectedRadioButton?.let {
                optionSelect = it.text.toString()
            }
        }
    }
}