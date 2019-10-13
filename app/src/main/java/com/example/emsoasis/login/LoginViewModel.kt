package com.example.emsoasis.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import io.reactivex.schedulers.Schedulers

class LoginViewModel(val loginService: LoginService) : ViewModel() {

    var error: LiveData<String> = MutableLiveData()
    var isLoading: LiveData<Boolean> = MutableLiveData()

    fun login(userName: String, password: String) {
        val body = JsonObject().apply {
            this.addProperty("username", userName)
            this.addProperty("password", password)
        }
        loginService.login(body).subscribeOn(Schedulers.io()).subscribe({response ->
            if (response.code() == 200) {
                var jwt = body.asString
            }
        },{

        })
    }

}