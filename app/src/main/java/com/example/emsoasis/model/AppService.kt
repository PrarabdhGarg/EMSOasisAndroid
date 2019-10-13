package com.example.emsoasis.model

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AppService {

    @POST("jwt/get_token/")
    fun login(@Body body: JsonObject): Single<Response<JsonObject>>

    @GET("events/app")
    fun getAllEvents(@Header("Authorization") jwt: String): Single<Response<AllEventsPojo>>

    // /events/app
    //  Authorization":"Bearer $jwtToken
    // https://emsoasis19.docs.apiary.io/#reference/0/list-of-events-app/get-event-details
}