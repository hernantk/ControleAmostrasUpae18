package com.example.intership.ui.update

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import com.example.intership.databinding.DialogRegisterUpdateLogBinding
import com.example.intership.domain.dto.LogDto
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
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
            loadRb()
    }

    private fun setupEvents() {
        binding.btnSave.setOnClickListener{update()}

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

        binding.btnDelete.setOnClickListener{delete()}

        binding.btnCamera.setOnClickListener{takePicture()}


    }

    private fun onSuccess(){
        onUpdate()
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
    private fun loadRb(){
        val image = Base64.decode(log.imgAmostras, Base64.DEFAULT)
        binding.imgAmostras.setImageBitmap(BitmapFactory.decodeByteArray(image,0, image.size))
        if(log.localDeColeta==binding.rbUpa.text){
            binding.rbUpa.isChecked=true
        }
        else{
            binding.rb18.isChecked=true
        }
    }

    private fun update() {
        viewModel.update(LogDto(log.id,log.date,
                        binding.tvEdtaQtd.text.toString(),
                        binding.tvSoroQtd.text.toString(),
                        binding.tvCitratoQtd.text.toString(),
                        binding.tvFezesQtd.text.toString(),
                        binding.tvUrinaQtd.text.toString(),
                        rbSelected(),
                        convertImage()))
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

    private fun convertImage(): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val image = binding.imgAmostras.drawToBitmap()
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)

    }
}