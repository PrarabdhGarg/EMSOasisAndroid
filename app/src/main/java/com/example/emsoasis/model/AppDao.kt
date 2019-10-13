package com.example.emsoasis.model

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable

interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEventData(events: List<EventsData>)

    @Query("DELETE FROM events_data")
    fun deleteEventsData()

    @Transaction
    fun updateEventsData(events: List<EventsData>){
        deleteEventsData()
        insertEventData(events)
    }

    @Query("SELECT * FROM events_data")
    fun getEventsData(): Flowable<List<EventsData>>
}