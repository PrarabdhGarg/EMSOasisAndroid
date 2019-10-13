package com.example.emsoasis.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.emsoasis.model.Repo

class ScannerViewModel(val repo: Repo): ViewModel(){

    @SuppressLint("CheckResult")
    fun addTeam(eventId: Int, name: String, participants: List<String>, leader: String){
        repo.addTeam(eventId, name, participants, leader).subscribe({

        },{

        })
    }

    @SuppressLint("CheckResult")
    fun addMember(eventId: Int, teamId: Int, participants: List<String>){
        repo.addMember(eventId, teamId, participants).subscribe({

        },{

        })
    }
}