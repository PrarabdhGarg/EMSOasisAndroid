package com.example.emsoasis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.emsoasis.EMSApp
import com.example.emsoasis.di.EventModule
import com.example.emsoasis.model.Repo
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ScannerViewModelFactory: ViewModelProvider.Factory{

    @Inject
    lateinit var repo: Repo

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        EMSApp.appComponent.newEventsComponent(EventModule()).injectScanner(this)
        return ScannerViewModel(repo) as T
    }
}