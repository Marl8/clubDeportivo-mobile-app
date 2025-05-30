package com.example.clubdeportivo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.clubdeportivo.R
import com.example.clubdeportivo.activities.InscribirSocioActivity
import com.example.clubdeportivo.activities.MenuActivity
import com.google.android.material.button.MaterialButton
import com.example.clubdeportivo.utils.ModalStyleUtils

class AptoFisicoDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(): AptoFisicoDialogFragment {
            return AptoFisicoDialogFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hacer el diálogo con fondo transparente
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_popup_apto_fisico, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
            setDimAmount(0.8f) // Opacidad del fondo oscuro
        }

        val btnConfirm: MaterialButton = view.findViewById(R.id.confirm)
        val btnCancel: MaterialButton = view.findViewById(R.id.cancel)

        btnConfirm.setOnClickListener {
            dismiss() // Cerrar el diálogo
            val intent = Intent(requireContext(), InscribirSocioActivity::class.java)
            intent.putExtra("aptoFisico", true)
            startActivity(intent)
        }

        btnCancel.setOnClickListener {
            dismiss() // Cerrar el diálogo
            val intent = Intent(requireContext(), MenuActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onStart() {
        super.onStart()
        ModalStyleUtils.setUIFragment(this)
    }
}