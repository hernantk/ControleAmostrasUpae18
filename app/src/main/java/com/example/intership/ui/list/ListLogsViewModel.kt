package com.example.intership.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.intership.domain.dto.LogDto
import com.example.intership.domain.repository.LogRepository

class ListLogsViewModel(private val repository: LogRepository) :ViewModel(){

    private val mLogsResult: MutableLiveData<List<LogDto>> = MutableLiveData()
    val logsResult: LiveData<List<LogDto>>
        get() = mLogsResult


    private val mLogsError: MutableLiveData<Unit> = MutableLiveData()
    val logsError: LiveData<Unit>
        get() = mLogsError

    fun listLogs(){
        repository.findAll(
           mLogsResult::postValue,
            mLogsError::postValue
        )

    }

}