package com.example.emsoasis.model

import com.example.emsoasis.model.retrofit.AllEventsPojo
import com.example.emsoasis.model.retrofit.AllTeamPojo
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface AppService {

    @POST("jwt/get_token/")
    fun login(@Body body: JsonObject): Single<Response<JsonObject>>

    @GET("events/app")
    fun getAllEvents(@Header("Authorization") jwt: String): Single<Response<AllEventsPojo>>

    @GET("events/{eventId}")
    fun getEventTeams(@Header("Authorization") jwt: String, @Path("eventId")eventId: Int): Single<Response<AllTeamPojo>>

    // events/"eventid"
    //  Authorization":"Bearer $jwtToken
    // https://emsoasis19.docs.apiary.io/#reference/0/list-of-events-app/get-event-details
}