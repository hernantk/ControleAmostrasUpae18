package com.example.intership.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.intership.domain.dto.RegisterDto
import com.example.intership.domain.repository.LogRepository
import java.time.LocalDate

class RegisterLogViewModel(private val repository: LogRepository) : ViewModel() {




    private val mOnSaveSuccess: MutableLiveData<Unit> = MutableLiveData()
    val onSaveSuccess: LiveData<Unit>
        get() = mOnSaveSuccess


    private val mLogsError: MutableLiveData<Unit> = MutableLiveData()
    val onSaveError: LiveData<Unit>
        get() = mLogsError


    fun save(log: RegisterDto){
        repository.save(log,
            { mOnSaveSuccess.postValue(Unit) },
            { mLogsError.postValue(Unit) })
    }
}