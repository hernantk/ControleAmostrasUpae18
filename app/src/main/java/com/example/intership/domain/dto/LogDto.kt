package com.example.intership.domain.dto

import java.time.LocalDateTime

data class LogDto (
    val id: String,
    val date:LocalDateTime,
    val edta:String,
    val soro:String,
    val citrato:String,
    val fezes:String,
    val urina:String,
    val localDeColeta:String,
)