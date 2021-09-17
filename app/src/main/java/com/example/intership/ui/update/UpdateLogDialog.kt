package com.example.intership.ui.update

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
import com.example.intership.domain.dto.LogDto
import com.example.intership.utils.convertBase64ToImage
import com.example.intership.utils.convertImageToBase64
import com.example.intership.utils.minus
import com.example.intership.utils.plus
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class UpdateLogDialog:BottomSheetDialogFragment() {

    private val binding : DialogRegisterUpdateLogBinding by lazy { DialogRegisterUpdateLogBinding.inflate(layoutInflater) }

    private val viewModel: UpdateLogViewModel by viewModel()

    lateinit var onUpdate:(() -> Unit)
    lateinit var log:LogDto
    private lateinit var pictureUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )=binding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUnitSpinner()
        setupEvents()
        loadValues()


    }

    private fun loadValues(){
        binding.btnDelete.visibility= View.VISIBLE
        binding.tvFezesQtd.text = log.fezes
        binding.tvUrinaQtd.text = log.urina
        binding.tvEdtaQtd.text = log.edta
        binding.tvCitratoQtd.text = log.citrato
        binding.tvSoroQtd.text = log.soro
        when(log.localDeColeta){
            "Upa"-> binding.spLocalColeta.setSelection(0)
            "Centro de Saude (18)" -> binding.spLocalColeta.setSelection(1)
        }
        binding.imgAmostras.setImageBitmap(convertBase64ToImage(log.imgAmostras))
    }

    private fun setupEvents() {
        binding.btnSave.setOnClickListener{update()}

        binding.btnEdtaPlus.setOnClickListener{ binding.tvEdtaQtd.text=plus(binding.tvEdtaQtd) }
        binding.btnEdtaMinus.setOnClickListener{binding.tvEdtaQtd.text= minus(binding.tvEdtaQtd)}

        binding.btnSoroPlus.setOnClickListener{ binding.tvSoroQtd.text=plus(binding.tvSoroQtd)  }
        binding.btnSoroMinus.setOnClickListener{binding.tvSoroQtd.text=minus(binding.tvSoroQtd)}

        binding.btnCitratoPlus.setOnClickListener{ binding.tvCitratoQtd.text=plus(binding.tvCitratoQtd) }
        binding.btnCitratoMinus.setOnClickListener{binding.tvCitratoQtd.text=minus(binding.tvCitratoQtd)}

        binding.btnFezesPlus.setOnClickListener{ binding.tvFezesQtd.text=plus(binding.tvFezesQtd) }
        binding.btnFezesMinus.setOnClickListener{binding.tvFezesQtd.text=minus(binding.tvFezesQtd)}

        binding.btnUrinaPlus.setOnClickListener{ binding.tvUrinaQtd.text=plus(binding.tvUrinaQtd) }
        binding.btnUrinaMinus.setOnClickListener{binding.tvUrinaQtd.text=minus(binding.tvUrinaQtd)}

        binding.btnDelete.setOnClickListener{delete()}
        binding.btnCamera.setOnClickListener{takePicture()}


    }

    private fun onSuccess(){
        onUpdate()
        dismiss()
    }


    private fun plusCitrato(){ binding.tvCitratoQtd.text = (binding.tvCitratoQtd.text.toString().toInt()+1).toString() }
    private fun minusCitrato(){ if(binding.tvCitratoQtd.text.toString().toInt()>0){binding.tvCitratoQtd.text = (binding.tvCitratoQtd.text.toString().toInt()-1).toString() }}

    private fun plusUrina(){ binding.tvUrinaQtd.text = (binding.tvUrinaQtd.text.toString().toInt()+1).toString() }
    private fun minusUrina(){ if(binding.tvUrinaQtd.text.toString().toInt()>0){binding.tvUrinaQtd.text = (binding.tvUrinaQtd.text.toString().toInt()-1).toString() }}

    private fun plusFezes(){ binding.tvFezesQtd.text = (binding.tvFezesQtd.text.toString().toInt()+1).toString() }
    private fun minusFezes(){ if(binding.tvFezesQtd.text.toString().toInt()>0){binding.tvFezesQtd.text = (binding.tvFezesQtd.text.toString().toInt()-1).toString() }}



    private fun update() {
        viewModel.update(LogDto(log.id,log.date,
            binding.tvEdtaQtd.text.toString(),
            binding.tvSoroQtd.text.toString(),
            binding.tvCitratoQtd.text.toString(),
            binding.tvFezesQtd.text.toString(),
            binding.tvUrinaQtd.text.toString(),
            binding.spLocalColeta.selectedItem.toString(),
            convertImageToBase64(binding.imgAmostras.drawToBitmap())))
        onSuccess()
    }
    private fun delete() {
        viewModel.delete(log.id)
        onSuccess()
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