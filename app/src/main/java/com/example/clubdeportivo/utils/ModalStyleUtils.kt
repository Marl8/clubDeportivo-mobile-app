package com.example.clubdeportivo.utils

import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

object ModalStyleUtils {

    // Configuración para AppCompatActivity
    fun setUI(activity: AppCompatActivity) {
        activity.supportActionBar?.hide() // Esto solo funciona si la actividad hereda de AppCompatActivity

        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            val width = windowMetrics.bounds.width() - insets.left - insets.right
            val height = windowMetrics.bounds.height() - insets.top - insets.bottom

            activity.window.setLayout((width * 0.8).toInt(), (height * 0.65).toInt())
        } else {
            @Suppress("DEPRECATION")
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = displayMetrics.widthPixels
            val height = displayMetrics.heightPixels

            activity.window.setLayout((width * 0.8).toInt(), (height * 0.65).toInt())
        }
        activity.window.setDimAmount(1f)
        activity.setFinishOnTouchOutside(true)
    }

    // Configuración para DialogFragment
    fun setUIFragment(dialogFragment: DialogFragment) {
        val activity = dialogFragment.requireActivity()
        val dialog = dialogFragment.dialog ?: return

        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            val width = windowMetrics.bounds.width() - insets.left - insets.right
            val height = windowMetrics.bounds.height() - insets.top - insets.bottom

            dialog.window?.setLayout((width * 0.8).toInt(), (height * 0.65).toInt())
        } else {
            @Suppress("DEPRECATION")
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = displayMetrics.widthPixels
            val height = displayMetrics.heightPixels

            dialog.window?.setLayout((width * 0.8).toInt(), (height * 0.65).toInt())
        }

        // Para DialogFragment, configuramos que se cierre al tocar fuera
        dialog.setCanceledOnTouchOutside(true)
    }
}
