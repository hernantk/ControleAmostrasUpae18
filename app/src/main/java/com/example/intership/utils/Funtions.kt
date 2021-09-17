package com.example.intership.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.TextView
import java.io.ByteArrayOutputStream

fun convertImageToBase64(image:Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT) }

fun convertBase64ToImage(textImage:String): Bitmap {
    val imageBitmap = Base64.decode(textImage,Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBitmap,0, imageBitmap.size)}

fun plus(binding: TextView):String{
    return (binding.text.toString().toInt()+1).toString()
}
fun minus(binding: TextView):String{
    return if(binding.text.toString().toInt()>0){
        (binding.text.toString().toInt()-1).toString()
    }
    else{
        binding.text.toString()
    }
}