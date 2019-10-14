package com.example.emsoasis.viewmodel

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emsoasis.asMut
import com.example.emsoasis.model.retrofit.AppService
import com.google.gson.JsonObject
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class LoginViewModel(val loginService: AppService, val sharedPreferences: SharedPreferences) : ViewModel() {

    var error: LiveData<String> = MutableLiveData()
    var isLoading: LiveData<Boolean> = MutableLiveData()
    var changePage: LiveData<Boolean> = MutableLiveData()

    init {
        changePage.asMut().postValue(false)
    }

    @SuppressLint("CheckResult")
    fun login(userName: String, password: String) {
        isLoading.asMut().postValue(true)
        val body = JsonObject().apply {
            this.addProperty("username", userName)
            this.addProperty("password", password)
        }
        loginService.login(body).subscribeOn(Schedulers.io()).subscribe({response ->
            if (response.code() == 200) {
                var body = JSONObject(response.body().toString())
                var jwt = body["access"]
                Log.d("LoginViewModel", "Recived jwt = $jwt")
                sharedPreferences.edit().putString("JWT", "Bearer $jwt").apply()
                isLoading.asMut().postValue(false)
                changePage.asMut().postValue(true)
            }
            else {
                Log.d("LoginViewModel", "Entered in other than 200.\n${response.code()}")
                Log.d("LoginViewModel", "Error message = ${JSONObject(response.body().toString())["message"].toString()}")
                error.asMut().postValue(JSONObject(response.body().toString())["message"].toString())
                isLoading.asMut().postValue(false)
            }
        },{
            isLoading.asMut().postValue(false)
            Log.e("LoginViewModel", "Error in Logging in = ${it.toString()}")
        })
    }

}