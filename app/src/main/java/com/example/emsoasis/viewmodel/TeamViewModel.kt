package com.example.emsoasis.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emsoasis.model.Repo
import com.example.emsoasis.model.retrofit.TeamPojo

@SuppressLint("CheckResult")
class TeamViewModel(val repo: Repo, val eventId: Int): ViewModel(){

    var teams: LiveData<List<TeamPojo>> = MutableLiveData()

    init {

        refreshTeams()
    }

    fun refreshTeams(){
        repo.getEventTeams(eventId).subscribe({
            when(it.code()){
                200 -> {
                    (teams as MutableLiveData).postValue(it.body()!!.teams)
                }

                else -> {

                }
            }
        },{

        })
    }
}