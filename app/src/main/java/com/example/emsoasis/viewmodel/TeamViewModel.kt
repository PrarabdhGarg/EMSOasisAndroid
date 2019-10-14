package com.example.emsoasis.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emsoasis.asMut
import com.example.emsoasis.model.Repo
import com.example.emsoasis.model.retrofit.TeamPojo

@SuppressLint("CheckResult")
class TeamViewModel(val repo: Repo, val eventId: Int): ViewModel(){

    var teams: LiveData<List<TeamPojo>> = MutableLiveData()
    var error: LiveData<String> = MutableLiveData()

    init {
        refreshTeams()
    }

    fun refreshTeams(){
        repo.getEventTeams(eventId).subscribe({
            when(it.code()){
                200 -> {
                    Log.d("TeamsViewModel", "Succesfully fetched Teams")
                    (teams as MutableLiveData).postValue(it.body()!!.teams)
                }
                else -> {
                    Log.e("TeamViewModel", "Response code not 200 ${it.code()}")
                    error.asMut().postValue(it.errorBody().toString())
                }
            }
        },{
            Log.e("TeamViewModel", "Error in network call ${it.message}")
            error.asMut().postValue("Unable to fetch data from network. Check your internet connection or Restart the app")
        })
    }
}