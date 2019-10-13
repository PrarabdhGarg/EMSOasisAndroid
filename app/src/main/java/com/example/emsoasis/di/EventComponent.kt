package com.example.emsoasis.di

import android.text.Editable
import com.example.emsoasis.viewmodel.EventViewModelFactory
import com.example.emsoasis.viewmodel.LoginViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [EventModule::class])
interface EventComponent {

    fun inject(factory: EventViewModelFactory)
    fun injectLoginViewModelFactory(factory:  LoginViewModelFactory)
}