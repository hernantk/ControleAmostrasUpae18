package com.example.intership.di

import com.example.intership.domain.repository.LogRepository
import com.example.intership.ui.list.ListLogsViewModel
import com.example.intership.ui.register.RegisterLogViewModel
import com.example.intership.ui.update.UpdateLogViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val repositoryModule = module{
    single { LogRepository(FirebaseFirestore.getInstance()) }
}

val viewModelModule = module{
   viewModel { ListLogsViewModel(get()) }
    viewModel { RegisterLogViewModel(get()) }
    viewModel { UpdateLogViewModel(get()) }
}

val adapterModule = module{
}