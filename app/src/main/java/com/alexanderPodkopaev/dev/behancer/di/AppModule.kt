package com.alexanderPodkopaev.dev.behancer.di

import androidx.room.Room
import com.alexanderPodkopaev.dev.behancer.AppDelegate
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.database.BehanceDatabase
import toothpick.config.Module
import toothpick.ktp.binding.bind

class AppModule(val mApp: AppDelegate) : Module() {

    init {
        bind(AppDelegate::class).toInstance(mApp)
        bind(Storage::class).toInstance(provideStorage())

    }

    private fun provideStorage(): Storage {
        val database = Room.databaseBuilder(mApp, BehanceDatabase::class.java, "behance_database")
                .fallbackToDestructiveMigration()
                .build()
        return Storage(database.behanceDao)
    }
}