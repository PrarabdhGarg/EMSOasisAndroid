package com.example.emsoasis.di

import android.app.Application
import dagger.Component
import dagger.Provides
import javax.inject.Singleton

@Singleton @Component(modules = [AppModule::class])
interface AppComponent {


}