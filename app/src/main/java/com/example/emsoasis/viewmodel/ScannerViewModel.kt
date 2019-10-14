package com.example.emsoasis.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emsoasis.asMut
import com.example.emsoasis.model.Repo

class ScannerViewModel(val repo: Repo): ViewModel(){

    var isSuccessful: LiveData<Boolean> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun addTeam(eventId: Int, name: String, participants: List<String>, leader: String){
        repo.addTeam(eventId, name, participants, leader).subscribe({
            Log.d("Scanner View Model", "REsponse = ${it.code()}")
            if(it.code() == 200) {
                isSuccessful.asMut().postValue(true)
            } else {
                Log.e("Scanner View Model", "Response = ${it.errorBody()}")
                isSuccessful.asMut().postValue(false)
            }
        },{
            isSuccessful.asMut().postValue(true)
            Log.e("Scanner View Model", "Error in Rx = ${it.message}")
        })
    }

    @SuppressLint("CheckResult")
    fun addMember(eventId: Int, teamId: Int, participants: List<String>){
        repo.addMember(eventId, teamId, participants).subscribe({
            Log.d("Scanner View Model", "REsponse = ${it.code()}")
            if(it.code() == 200) {
                isSuccessful.asMut().postValue(true)
            } else {
                Log.e("Scanner View Model", "Response = ${it.errorBody()}")
                isSuccessful.asMut().postValue(false)
            }
        },{
            isSuccessful.asMut().postValue(true)
            Log.e("Scanner View Model", "Error in Rx = ${it.message}")
        })
    }
}