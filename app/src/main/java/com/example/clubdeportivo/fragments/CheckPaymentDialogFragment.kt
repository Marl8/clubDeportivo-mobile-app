package com.example.clubdeportivo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.clubdeportivo.R
import com.example.clubdeportivo.activities.PagarCuotaActivity
import com.google.android.material.button.MaterialButton

class CheckPaymentDialogFragment : DialogFragment() {

    private var statePayment: Boolean = false

    companion object {
        private const val STATE_PAYMENT = "statePayment"

        fun newInstance(statePayment: Boolean): CheckPaymentDialogFragment {
            val fragment = CheckPaymentDialogFragment()
            val args = Bundle()
            args.putBoolean(STATE_PAYMENT, statePayment)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            statePayment = it.getBoolean(STATE_PAYMENT, false)
        }

        // Hacer el diálogo en pantalla completa
        setStyle(STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_check_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val checkImg: ImageView = view.findViewById(R.id.payment_icon)
        val message: TextView = view.findViewById(R.id.txtMessage)
        val btnOk: MaterialButton = view.findViewById(R.id.btnOk)

        if (statePayment) {
            // Mostrar imagen de check y mensaje de éxito
            checkImg.setImageResource(R.drawable.ic_check_ok_icon)
            message.text = "Operación completada con éxito"
        } else {
            // Mostrar imagen de error y mensaje de fallo
            checkImg.setImageResource(R.drawable.ic_error_icon)
            message.text = "Error en la operación"
        }

        btnOk.setOnClickListener {
            /*val intent = Intent(requireContext(), PagarCuotaActivity::class.java)
            startActivity(intent)*/
            requireActivity().finish()
        }
    }
}