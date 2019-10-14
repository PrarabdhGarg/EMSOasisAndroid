package com.example.emsoasis.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emsoasis.asMut
import com.example.emsoasis.model.room.EventsData
import com.example.emsoasis.model.Repo

@SuppressLint("CheckResult")
class EventViewModel(val repo: Repo): ViewModel(){

    var events: LiveData<List<EventsData>> = MutableLiveData()
    var error: LiveData<String> =  MutableLiveData()

    init {

        repo.getEventsData().subscribe({
            (events as MutableLiveData).postValue(it)
        },{
            Log.e("EventsViewModel", "Error in getting event data = ${it.message}")
            error.asMut().postValue("Interfnal Error. Please restart the app")
        })
    }

    fun refreshEvents(){
        repo.updateEventsData().subscribe({
            Log.d("EventsViewModel", "Events Data Successfully refreshed")
        },{
            Log.e("EventsViewModel", "Error in refreshing event data = ${it.message}")
            error.asMut().postValue("Interfnal Error. Please restart the app")
        })
    }
}