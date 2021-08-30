package com.example.intership.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.intership.databinding.DialogRegisterUpdateLogBinding
import com.example.intership.domain.dto.RegisterDto
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime

class RegisterLogDialog:BottomSheetDialogFragment() {

    private val binding : DialogRegisterUpdateLogBinding by lazy { DialogRegisterUpdateLogBinding.inflate(layoutInflater) }

    private val viewModel: RegisterLogViewModel by viewModel()

    lateinit var onSave:(() -> Unit)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )=binding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEvents()

    }

    private fun setupEvents() {
        binding.btnSave.setOnClickListener{save()}

        binding.btnEdtaPlus.setOnClickListener{ plusEdta() }
        binding.btnEdtaMinus.setOnClickListener{minusEdta()}

        binding.btnSoroPlus.setOnClickListener{ plusSoro() }
        binding.btnSoroMinus.setOnClickListener{minusSoro()}

        binding.btnCitratoPlus.setOnClickListener{ plusCitrato() }
        binding.btnCitratoMinus.setOnClickListener{minusCitrato()}

        binding.btnFezesPlus.setOnClickListener{ plusFezes() }
        binding.btnFezesMinus.setOnClickListener{minusFezes()}

        binding.btnUrinaPlus.setOnClickListener{ plusUrina() }
        binding.btnUrinaMinus.setOnClickListener{minusUrina()}


    }

    private fun onSaveSuccess(){
        onSave()
        dismiss()
    }

    private fun plusEdta(){ binding.tvEdtaQtd.text = (binding.tvEdtaQtd.text.toString().toInt()+1).toString() }
    private fun minusEdta(){ if(binding.tvEdtaQtd.text.toString().toInt()>0){binding.tvEdtaQtd.text = (binding.tvEdtaQtd.text.toString().toInt()-1).toString() }}

    private fun plusSoro(){ binding.tvSoroQtd.text = (binding.tvSoroQtd.text.toString().toInt()+1).toString() }
    private fun minusSoro(){ if(binding.tvSoroQtd.text.toString().toInt()>0){binding.tvSoroQtd.text = (binding.tvSoroQtd.text.toString().toInt()-1).toString() }}

    private fun plusCitrato(){ binding.tvCitratoQtd.text = (binding.tvCitratoQtd.text.toString().toInt()+1).toString() }
    private fun minusCitrato(){ if(binding.tvCitratoQtd.text.toString().toInt()>0){binding.tvCitratoQtd.text = (binding.tvCitratoQtd.text.toString().toInt()-1).toString() }}

    private fun plusUrina(){ binding.tvUrinaQtd.text = (binding.tvUrinaQtd.text.toString().toInt()+1).toString() }
    private fun minusUrina(){ if(binding.tvUrinaQtd.text.toString().toInt()>0){binding.tvUrinaQtd.text = (binding.tvUrinaQtd.text.toString().toInt()-1).toString() }}

    private fun plusFezes(){ binding.tvFezesQtd.text = (binding.tvFezesQtd.text.toString().toInt()+1).toString() }
    private fun minusFezes(){ if(binding.tvFezesQtd.text.toString().toInt()>0){binding.tvFezesQtd.text = (binding.tvFezesQtd.text.toString().toInt()-1).toString() }}

    private fun rbSelected():String{
        return if(binding.rbUpa.isChecked){
            binding.rbUpa.text.toString()
        } else{
            binding.rb18.text.toString()
        }
    }

    private fun save() {
        viewModel.save(RegisterDto(LocalDateTime.now().toString(),
                        binding.tvEdtaQtd.text.toString().toInt().toString(),
                        binding.tvSoroQtd.text.toString().toInt().toString(),
                        binding.tvCitratoQtd.text.toString().toInt().toString(),
                        binding.tvFezesQtd.text.toString().toInt().toString(),
                        binding.tvUrinaQtd.text.toString().toInt().toString(),
                        rbSelected()))
        onSaveSuccess()
    }
}