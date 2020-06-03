package com.alexanderPodkopaev.dev.behancer

import android.app.Application
import androidx.room.Room
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.database.BehanceDatabase
import com.alexanderPodkopaev.dev.behancer.di.AppModule
import com.alexanderPodkopaev.dev.behancer.di.NetworkModule
import toothpick.Scope
import toothpick.Toothpick
import toothpick.configuration.Configuration



class AppDelegate : Application() {
    companion object {
        lateinit var sAppScope : Scope
    }

    override fun onCreate() {
        super.onCreate()
        Toothpick.setConfiguration(Configuration.forProduction())
        sAppScope = Toothpick.openScope(AppDelegate::class)
        sAppScope.installModules(NetworkModule(), AppModule(this))

    }

}