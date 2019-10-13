package com.example.emsoasis.model.retrofit

import com.google.gson.annotations.SerializedName

data class MemberPojo(

    @SerializedName("name")
    val name: String,

    @SerializedName("college")
    val college: String,

    @SerializedName("ems_code")
    val ems: String
)