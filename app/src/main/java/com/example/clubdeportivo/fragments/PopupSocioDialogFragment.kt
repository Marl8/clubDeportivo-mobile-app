package com.example.clubdeportivo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.clubdeportivo.R
import com.example.clubdeportivo.controllers.CuotaController
import com.example.clubdeportivo.controllers.SocioController
import com.example.clubdeportivo.entities.Socio
import com.example.clubdeportivo.repositories.CuotaRepository
import com.example.clubdeportivo.repositories.SocioRepository
import com.example.clubdeportivo.utils.ModalStyleUtils
import com.google.android.material.button.MaterialButton

class PopupSocioDialogFragment : DialogFragment() {

    private lateinit var cuotaController: CuotaController
    private lateinit var socioController: SocioController

    companion object {
        fun newInstance(args: Bundle): PopupSocioDialogFragment {
            val fragment = PopupSocioDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popup_confirm_data_socio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setCanceledOnTouchOutside(false)

        dialog?.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
            setDimAmount(0.9f) // Opacidad del fondo oscuro
        }

        cuotaController = CuotaController(CuotaRepository(requireContext()), SocioRepository(requireContext()))
        socioController = SocioController(SocioRepository(requireContext()))

        val btnConfirm: MaterialButton = view.findViewById(R.id.confirm)
        val btnCancel: MaterialButton = view.findViewById(R.id.cancel)
        val txtId: TextView = view.findViewById(R.id.idSocio)
        val txtName: TextView = view.findViewById(R.id.nombre_socio)
        val txtDni: TextView = view.findViewById(R.id.dni)
        val txtValue: TextView = view.findViewById(R.id.value)
        val txtPayMethod: TextView = view.findViewById(R.id.payMethod)
        val txtNumCuotas: TextView = view.findViewById(R.id.numCuotas)


        val args = requireArguments()
        val nameSocio = args.getString("nombreSocio")
        val lastName = args.getString("apellidoSocio")
        val id = args.getString("idSocio")
        val dni = args.getString("dniSocio")
        val email = args.getString("emailSocio")
        val state = args.getString("stateSocio")
        val phone = args.getString("dniPhone")
        val apto = args.getString("aptoFisico")
        val valueCuota = args.getString("valueCuota")
        val payMethod = args.getString("payMethod")
        val numCuotas = args.getString("numberCuotas")


        txtId.text = id
        txtName.text = "$nameSocio $lastName"
        txtDni.text = dni
        txtValue.text = valueCuota
        txtPayMethod.text = payMethod
        txtNumCuotas.text = numCuotas

        val numberCuotas: Int = when (numCuotas?.trim()) {
            "1 Cuota" -> 1
            "3 Cuotas" -> 3
            "6 Cuotas" -> 6
            else -> 1
        }

        btnConfirm.setOnClickListener {
            var dialogState = false
            if (payMethod != null && valueCuota != null && nameSocio != null && lastName != null
                && dni != null && email != null && phone != null) {

                cuotaController.payCuota(payMethod, numberCuotas, valueCuota.toDouble(), dni)

                dialogState = true
                val aptoFisico = apto.toBoolean()
                var newState = state.toBoolean()
                if(!newState){
                    newState = true
                }
                val socio = Socio(id?.toInt(), newState, aptoFisico, nameSocio, lastName, dni, email, phone)
                socioController.updateState(socio.idSocio, newState)
            }
            showCheckPaymentDialog(dialogState)
            dismiss()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun showCheckPaymentDialog(success: Boolean) {
        val dialog = CheckPaymentDialogFragment.newInstance(success)
        dialog.show(parentFragmentManager, "CheckPaymentDialog")
    }

    override fun onStart() {
        super.onStart()
        ModalStyleUtils.setUIFragment(this)
    }
}