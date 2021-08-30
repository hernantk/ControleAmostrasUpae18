package com.example.intership.ui.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.intership.domain.dto.LogDto
import com.example.intership.domain.dto.RegisterDto
import com.example.intership.domain.repository.LogRepository
import java.time.LocalDate

class UpdateLogViewModel(private val repository: LogRepository) : ViewModel() {


    fun update(log: LogDto){
        repository.update(log)
    }
    fun delete(logId: String){
        repository.delete(logId)
    }
}