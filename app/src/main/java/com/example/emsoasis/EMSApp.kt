package com.example.emsoasis

import android.app.Application
import com.example.emsoasis.di.AppComponent
import dagger.internal.DaggerCollections

class EMSApp: Application(){

    companion object{
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        
    }
}