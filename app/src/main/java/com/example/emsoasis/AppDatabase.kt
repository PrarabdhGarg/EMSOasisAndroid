package com.example.emsoasis

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.emsoasis.model.AppDao
import com.example.emsoasis.model.room.EventsData

@Database(entities = [EventsData::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun appDao(): AppDao
}