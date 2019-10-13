package com.example.emsoasis.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.emsoasis.EMSApp
import com.example.emsoasis.di.EventModule
import com.example.emsoasis.model.retrofit.AppService
import javax.inject.Inject

class LoginViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var service: AppService

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        EMSApp.appComponent.newEventsComponent(EventModule()).injectLoginViewModelFactory(this)
        return LoginViewModel(service,  sharedPreferences) as T
    }

}