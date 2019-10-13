package com.example.emsoasis.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.emsoasis.AppDatabase
import com.example.emsoasis.BaseInterceptor
import com.example.emsoasis.model.room.AppDao
import com.example.emsoasis.model.retrofit.AppService
import com.example.emsoasis.model.Repo
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application{
        return application
    }

    @Provides
    @Singleton
    fun provideAppService(retrofit: Retrofit): AppService {
        return retrofit.create(AppService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase{
        return Room.databaseBuilder(application, AppDatabase::class.java, "ems.db").fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://testwallet.bits-oasis.org/ems/")
            .client(OkHttpClient().newBuilder().addInterceptor(BaseInterceptor()).build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAppDao(appDatabase: AppDatabase): AppDao {
        return appDatabase.appDao()
    }

    @Provides
    @Singleton
    fun provideRepo(appService: AppService, appDao: AppDao, sharedPreferences: SharedPreferences): Repo{
        return Repo(appService, appDao, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSharedPreference(application: Application): SharedPreferences {
        return application.getSharedPreferences("ems.sp", Context.MODE_PRIVATE)
    }
}