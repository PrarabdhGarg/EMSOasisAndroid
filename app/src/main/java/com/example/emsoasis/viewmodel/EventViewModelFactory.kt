@file:Suppress("UNCHECKED_CAST")

package com.example.emsoasis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.emsoasis.model.Repo
import javax.inject.Inject

class EventViewModelFactory:ViewModelProvider.Factory{

    @Inject
    lateinit var repo: Repo

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventViewModel(repo) as T
    }

}