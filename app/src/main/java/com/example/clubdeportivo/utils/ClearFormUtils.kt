package com.example.clubdeportivo.utils

import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner

object ClearFormUtils {

    fun clearForm(viewGroup: ViewGroup) {
        for (i in 0 until viewGroup.childCount) {
            val view = viewGroup.getChildAt(i)
            when (view) {
                is EditText -> view.text.clear()
                is CheckBox -> view.isChecked = false
                is RadioButton -> view.isChecked = false
                is Spinner -> view.setSelection(0)
                is ViewGroup -> clearForm(view) // Recursivo para limpiar hijos
            }
        }
    }
}