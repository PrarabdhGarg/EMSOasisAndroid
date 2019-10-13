package com.example.emsoasis.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emsoasis.model.EventsData
import com.example.emsoasis.model.Repo

@SuppressLint("CheckResult")
class EventViewModel(val repo: Repo): ViewModel(){

    var events: LiveData<List<EventsData>> = MutableLiveData()

    init {

        repo.getEventsData().subscribe({
            (events as MutableLiveData).postValue(it)
        },{

        })
    }

    fun refreshEvents(){
        repo.updateEventsData().subscribe({

        },{

        })
    }
}