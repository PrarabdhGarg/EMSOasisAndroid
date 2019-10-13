package com.example.emsoasis.model.retrofit

import com.example.emsoasis.model.retrofit.EventPojo
import com.google.gson.annotations.SerializedName

data class AllEventsPojo(

    @SerializedName("events")
    val events: List<EventPojo>
)