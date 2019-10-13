package com.example.emsoasis.model

import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class Repo(val appService: AppService, val appDao: AppDao, val sharedPreferences: SharedPreferences){

    init {
        updateEventsData().subscribe()
    }

    fun updateEventsData(): Completable{
        return appService.getAllEvents("").subscribeOn(Schedulers.io())
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

    fun EventPojo.toEventsData(): EventsData{
        return EventsData(id = id, name = name, maxPlayers = maxTeamSize, minPlayers = minTeamSize)
    }

}