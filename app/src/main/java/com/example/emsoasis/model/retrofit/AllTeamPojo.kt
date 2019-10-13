package com.example.emsoasis.model.retrofit

import com.google.gson.annotations.SerializedName

data class AllTeamPojo(

    @SerializedName("teams_info")
    val teams: List<TeamPojo>
)