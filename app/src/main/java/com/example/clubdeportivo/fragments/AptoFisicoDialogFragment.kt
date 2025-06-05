package com.example.clubdeportivo.fragments

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.clubdeportivo.R
import com.example.clubdeportivo.activities.InscribirNoSocioActivity
import com.example.clubdeportivo.activities.InscribirSocioActivity
import com.example.clubdeportivo.activities.MenuActivity
import com.google.android.material.button.MaterialButton
import com.example.clubdeportivo.utils.ModalStyleUtils

class AptoFisicoDialogFragment : DialogFragment() {

    private var isSocio: Boolean = false

    companion object {

        private const val IS_SOCIO = "statePayment"

        fun newInstance(isSocio: Boolean): AptoFisicoDialogFragment {
            val fragment = AptoFisicoDialogFragment()
            val args = Bundle()
            args.putBoolean(IS_SOCIO, isSocio)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hacer el diálogo con fondo transparente
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Dialog)
        arguments?.let {
            isSocio = it.getBoolean(IS_SOCIO, false)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_popup_apto_fisico, container, false)
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
            if (isSocio) {
                val intent = Intent(requireContext(), InscribirSocioActivity::class.java)
                intent.putExtra("aptoFisico", true)
                startActivity(intent)
            }else{
                val intent = Intent(requireContext(), InscribirNoSocioActivity::class.java)
                intent.putExtra("aptoFisico", true)
                startActivity(intent)
            }
        }

        btnCancel.setOnClickListener {
            dismiss() // Cerrar el diálogo
            val intent = Intent(requireContext(), MenuActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(requireContext(),
                R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())
            requireActivity().finish()
        }
    }

    override fun onStart() {
        super.onStart()
        ModalStyleUtils.setUIFragment(this)
    }
}