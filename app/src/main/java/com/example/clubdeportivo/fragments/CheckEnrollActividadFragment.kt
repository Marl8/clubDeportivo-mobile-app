package com.example.clubdeportivo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.clubdeportivo.R
import com.google.android.material.button.MaterialButton

class CheckEnrollActividadFragment: DialogFragment() {

    private var enrollMessage: String = ""
    private var enrollSuccess: Boolean = false

    companion object {
        private const val ENROLL_MESSAGE = "message"
        private const val ENROLL_SUCCESS = "success"

        fun newInstance(message: String, success: Boolean): CheckEnrollActividadFragment {
            val fragment = CheckEnrollActividadFragment()
            val args = Bundle()
            args.putString(ENROLL_MESSAGE, message)
            args.putBoolean(ENROLL_SUCCESS,success)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            enrollMessage = it.getString(ENROLL_MESSAGE, "")
            enrollSuccess = it.getBoolean(ENROLL_SUCCESS, false)
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
        val txtView: TextView = view.findViewById(R.id.txtMessage)
        val btnOk: MaterialButton = view.findViewById(R.id.btnOk)

        if (enrollMessage == "Inscripción realizada con éxito." && enrollSuccess) {
            // Mostrar imagen de check y mensaje de éxito
            checkImg.setImageResource(R.drawable.ic_check_ok_icon)
            txtView.text = enrollMessage
        } else {
            // Mostrar imagen de error y mensaje de fallo
            checkImg.setImageResource(R.drawable.ic_error_icon)
            txtView.text = enrollMessage
        }
        btnOk.setOnClickListener {
            requireActivity().finish()
        }
    }
}