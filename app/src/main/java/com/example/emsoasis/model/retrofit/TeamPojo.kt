package com.example.emsoasis.model.retrofit

import com.google.gson.annotations.SerializedName

data class TeamPojo(

    @SerializedName("name")
    val name: String,

    @SerializedName("id")
    val id: Int
)