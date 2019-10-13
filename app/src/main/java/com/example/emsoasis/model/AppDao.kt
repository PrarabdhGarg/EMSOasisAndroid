package com.example.emsoasis.model

import androidx.room.*
import com.example.emsoasis.model.room.EventsData
import io.reactivex.Flowable

@Dao
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