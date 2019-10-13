package com.example.emsoasis

import android.app.Application
import android.util.Log
import com.example.emsoasis.di.AppComponent
import com.example.emsoasis.di.AppModule
import com.example.emsoasis.di.DaggerAppComponent
import io.reactivex.plugins.RxJavaPlugins

class EMSApp: Application(){

    companion object{
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

        RxJavaPlugins.setErrorHandler {
            Log.e("App", "error $it: ${it.message ?: "No message"}")
            // throw it
        }
    }
}