package com.example.intership.ui.image

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intership.R
import com.example.intership.databinding.ActivityImageBinding
import com.example.intership.databinding.ActivityListLogsBinding
import com.example.intership.domain.dto.LogDto
import com.example.intership.ui.register.RegisterLogDialog
import com.example.intership.ui.update.UpdateLogDialog
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream


class ImageActivity : AppCompatActivity() {

    private val binding: ActivityImageBinding by lazy { ActivityImageBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupImage()

    }

    private fun setupImage() {
        val imageBitmap = Base64.decode(getLog().imgAmostras,Base64.DEFAULT)
        binding.imgAmostras.setImageBitmap(BitmapFactory.decodeByteArray(imageBitmap,0, imageBitmap.size))
    }

    private fun getLog() : LogDto{
        return intent.extras!!.get("LOG") as LogDto
    }

}