package com.example.intership.domain.repository

import com.example.intership.domain.dto.LogDto
import com.example.intership.domain.dto.RegisterDto
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import kotlin.reflect.KFunction1

class LogRepository(private val firestore : FirebaseFirestore) {


    fun save(log:RegisterDto, onSuccess:()-> Unit, onFailure:() -> Unit){
        firestore.collection(LOGS_COLLETION)
            .add(log)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }
    

    fun findAll(onSuccess: (List<LogDto>) -> Unit, onFailure: KFunction1<Unit, Unit>){


        firestore.collection(LOGS_COLLETION).orderBy("date")
            .get()
            .addOnSuccessListener { logs ->
            val result = logs.map{ log ->
                LogDto(log.id,
                    LocalDateTime.parse(log.getString("date")),
                    log.getString("edta") ?:"0",
                    log.getString("soro") ?:"0",
                    log.getString("citrato") ?:"0",
                    log.getString("fezes") ?:"0",
                    log.getString("urina") ?:"0",
                    log.getString("localDeColeta")?:"",
                    log.getString("imgAmostras")?:"")
            }
                onSuccess(result)
            }
            .addOnFailureListener { onFailure(Unit)}

    }

    fun update( log:LogDto){
        firestore.collection(LOGS_COLLETION)
            .document(log.id).update(mapOf(
                "edta" to log.edta,
                "soro" to log.soro,
                "citrato" to log.citrato,
                "fezes" to log.fezes,
                "urina" to log.urina,
                "localDeColeta" to log.localDeColeta,
                "imgAmostras" to log.imgAmostras

            ))
    }

    fun delete( logId:String){firestore.collection(LOGS_COLLETION).document(logId).delete() }

    companion object{
        private const val LOGS_COLLETION = "logs"
        private const val FIELD_DATE = "date"
        private const val FIELD_CONTENT = "content"
    }

}