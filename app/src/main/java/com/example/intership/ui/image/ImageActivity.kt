package com.example.intership.ui.image

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import com.example.intership.databinding.ActivityImageBinding
import com.example.intership.domain.dto.LogDto


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