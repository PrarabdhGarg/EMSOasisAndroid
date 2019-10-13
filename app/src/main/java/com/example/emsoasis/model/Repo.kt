package com.example.emsoasis.model

import android.content.SharedPreferences
import com.example.emsoasis.model.retrofit.AllMemberPojo
import com.example.emsoasis.model.retrofit.AllTeamPojo
import com.example.emsoasis.model.retrofit.AppService
import com.example.emsoasis.model.retrofit.EventPojo
import com.example.emsoasis.model.room.AppDao
import com.example.emsoasis.model.room.EventsData
import com.google.gson.JsonObject
import com.squareup.moshi.Json
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class Repo(val appService: AppService, val appDao: AppDao, val sharedPreferences: SharedPreferences){

    init {

        updateEventsData().subscribe()
    }

    fun updateEventsData(): Completable{
        return appService.getAllEvents(sharedPreferences.getString("JWT", "")!!).subscribeOn(Schedulers.io())
            .doOnSuccess {response ->

                when(response.code()){

                    200 ->{
                        var events: MutableList<EventsData> = arrayListOf()
                        response.body()!!.events.forEach {
                            events.add(it.toEventsData())
                        }

                        appDao.updateEventsData(events)
                    }

                    else -> {

                    }
                }
            }
            .ignoreElement()
    }

    fun getEventsData(): Flowable<List<EventsData>>{
        return appDao.getEventsData().subscribeOn(Schedulers.io())
    }

    fun EventPojo.toEventsData(): EventsData {
        return EventsData(
            id = id,
            name = name,
            maxPlayers = maxTeamSize,
            minPlayers = minTeamSize
        )
    }

    fun getEventTeams(eventId: Int): Single<Response<AllTeamPojo>>{
        return appService.getEventTeams(sharedPreferences.getString("JWT", "")!!, eventId).subscribeOn(Schedulers.io())
    }

    fun getTeamMembers(eventId: Int, teamId: Int): Single<Response<AllMemberPojo>>{
        return appService.getTeamMembers(sharedPreferences.getString("JWT", "")!!, eventId, teamId).subscribeOn(Schedulers.io())
    }

    fun addTeam(eventId: Int, name: String, participants: List<String>, leader: String): Completable{

        var body = JsonObject()
        body.addProperty("name", name)
        body.addProperty("participants", participants.toString())
        body.addProperty("leader", leader)

        return appService.addTeam(sharedPreferences.getString("JWT", "")!!, eventId, body).subscribeOn(Schedulers.io())
            .ignoreElement()
    }

    fun addMember(eventId: Int, teamId: Int, participants: List<String>): Completable{

        var body = JsonObject()
        body.addProperty("qr_codes", participants.toString())

        return appService.addMember(sharedPreferences.getString("JWT", "")!!, eventId, teamId, body).subscribeOn(Schedulers.io())
            .ignoreElement()
    }
}