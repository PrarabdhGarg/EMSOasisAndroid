package com.example.emsoasis.model

import com.google.gson.annotations.SerializedName

data class AllEventsPojo(

    @SerializedName("events")
    val events: List<EventPojo>
)