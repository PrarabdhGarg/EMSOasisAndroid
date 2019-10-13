package com.example.emsoasis.model

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AppService {

    @POST("/jwt/get_token/")
    fun login(@Body body: JsonObject): Single<Response<JsonObject>>
}