package com.example.emsoasis.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emsoasis.asMut
import com.example.emsoasis.model.Repo
import com.example.emsoasis.model.retrofit.MemberPojo
import io.reactivex.schedulers.Schedulers

class MemberViewModel(val eventId: String, val teamId: String, val repo: Repo) : ViewModel() {
    var members: LiveData<List<MemberPojo>> = MutableLiveData()

    init {
        refreshEvents()
    }

    fun refreshEvents(){
        repo.getTeamMembers(eventId = eventId.toInt(), teamId = teamId.toInt()).subscribeOn(Schedulers.io()).subscribe({
            if (it.code() == 200) {
                members.asMut().postValue(it.body()!!.members)
            }
        },{
            Log.e("Member View Model", "Error occoured = ${it.toString()}")
        })
    }
}