package com.alexanderPodkopaev.dev.behancer

import android.app.Application
import androidx.room.Room
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.database.BehanceDatabase


class AppDelegate : Application() {
    var storage: Storage? = null
        private set

    override fun onCreate() {
        super.onCreate()
        val database = Room.databaseBuilder(this,BehanceDatabase::class.java , "behance_database")
                .fallbackToDestructiveMigration()
                .build()
        storage = database.behanceDao?.let { Storage(it) }
    }

}