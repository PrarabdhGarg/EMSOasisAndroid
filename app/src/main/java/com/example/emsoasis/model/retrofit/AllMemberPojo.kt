package com.example.emsoasis.model.retrofit

import com.google.gson.annotations.SerializedName

data class AllMemberPojo(

    @SerializedName("participations_info")
    val members: List<MemberPojo>
)