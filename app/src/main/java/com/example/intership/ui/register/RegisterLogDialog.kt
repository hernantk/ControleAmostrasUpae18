package com.example.intership.ui.register

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import com.example.intership.R
import com.example.intership.databinding.DialogRegisterUpdateLogBinding
import com.example.intership.domain.dto.RegisterDto
import com.example.intership.utils.convertImageToBase64
import com.example.intership.utils.minus
import com.example.intership.utils.plus
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.time.LocalDateTime

class RegisterLogDialog:BottomSheetDialogFragment() {

    private val binding : DialogRegisterUpdateLogBinding by lazy { DialogRegisterUpdateLogBinding.inflate(layoutInflater) }

    private val viewModel: RegisterLogViewModel by viewModel()

    lateinit var onSave:(() -> Unit)
    private lateinit var pictureUri: Uri



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )=binding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEvents()
        setupUnitSpinner()

    }



    private fun setupEvents() {
        binding.btnSave.setOnClickListener{save()}

        binding.btnEdtaPlus.setOnClickListener{ binding.tvEdtaQtd.text= plus(binding.tvEdtaQtd) }
        binding.btnEdtaMinus.setOnClickListener{binding.tvEdtaQtd.text= minus(binding.tvEdtaQtd) }

        binding.btnSoroPlus.setOnClickListener{ binding.tvSoroQtd.text= plus(binding.tvSoroQtd)  }
        binding.btnSoroMinus.setOnClickListener{binding.tvSoroQtd.text= minus(binding.tvSoroQtd) }

        binding.btnCitratoPlus.setOnClickListener{ binding.tvCitratoQtd.text= plus(binding.tvCitratoQtd) }
        binding.btnCitratoMinus.setOnClickListener{binding.tvCitratoQtd.text= minus(binding.tvCitratoQtd) }

        binding.btnFezesPlus.setOnClickListener{ binding.tvFezesQtd.text= plus(binding.tvFezesQtd) }
        binding.btnFezesMinus.setOnClickListener{binding.tvFezesQtd.text= minus(binding.tvFezesQtd) }

        binding.btnUrinaPlus.setOnClickListener{ binding.tvUrinaQtd.text= plus(binding.tvUrinaQtd) }
        binding.btnUrinaMinus.setOnClickListener{binding.tvUrinaQtd.text= minus(binding.tvUrinaQtd) }

        binding.btnCamera.setOnClickListener{takePicture()}


    }

    private fun onSaveSuccess(){
        onSave()
        dismiss()
    }




    private fun save() {
        viewModel.save(RegisterDto(LocalDateTime.now().toString(),
            binding.tvEdtaQtd.text.toString().toInt().toString(),
            binding.tvSoroQtd.text.toString().toInt().toString(),
            binding.tvCitratoQtd.text.toString().toInt().toString(),
            binding.tvFezesQtd.text.toString().toInt().toString(),
            binding.tvUrinaQtd.text.toString().toInt().toString(),
            binding.spLocalColeta.selectedItem.toString(),
            convertImageToBase64(binding.imgAmostras.drawToBitmap())))
        onSaveSuccess()
    }


    private fun takePicture() {
        createPictureFile()
        takePictureLauncher.launch(pictureUri)

    }
    private fun createPictureFile() {
        val pictureDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val pictureFile = File(pictureDir,"pic1")

        this.pictureUri =
            FileProvider.getUriForFile(requireActivity(), "com.example.intership.fileprovider", pictureFile)
    }
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { pictureTaken ->
        if (pictureTaken) {
            binding.imgAmostras.setImageURI(this.pictureUri)

        }
    }

    private fun setupUnitSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.todos_locais_coleta,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spLocalColeta.adapter = adapter
    }
}