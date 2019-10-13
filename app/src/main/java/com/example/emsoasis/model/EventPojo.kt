package com.example.emsoasis.model

import com.google.gson.annotations.SerializedName

data class EventPojo(

    val name: String,

    val id: Int,

    @SerializedName("max_team_size")
    val maxTeamSize: Int,

    @SerializedName("min_team_size")
    val minTeamSize: Int
)