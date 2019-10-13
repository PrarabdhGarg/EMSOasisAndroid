package com.example.emsoasis.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events_data")
data class EventsData(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "max")
    val maxPlayers: Int,

    @ColumnInfo(name = "min")
    val minPlayers: Int
)