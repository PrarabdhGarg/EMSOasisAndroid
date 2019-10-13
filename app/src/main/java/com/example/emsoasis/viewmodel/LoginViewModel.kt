package com.example.emsoasis.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emsoasis.model.AppService
import com.google.gson.JsonObject
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class LoginViewModel(val loginService: AppService) : ViewModel() {

    var error: LiveData<String> = MutableLiveData()
    var isLoading: LiveData<Boolean> = MutableLiveData()

    fun login(userName: String, password: String) {
        val body = JsonObject().apply {
            this.addProperty("username", userName)
            this.addProperty("password", password)
        }
        loginService.login(body).subscribeOn(Schedulers.io()).subscribe({response ->
            if (response.code() == 200) {
                var body = JSONObject(response.body().toString())

            }
        },{

        })
    }

}