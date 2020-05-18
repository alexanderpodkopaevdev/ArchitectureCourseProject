package com.alexanderPodkopaev.dev.behancer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexanderPodkopaev.dev.behancer.data.model.project.Cover
import com.alexanderPodkopaev.dev.behancer.data.model.project.Owner
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import com.alexanderPodkopaev.dev.behancer.data.model.user.Image
import com.alexanderPodkopaev.dev.behancer.data.model.user.User


@Database(entities = [Project::class, Cover::class, Owner::class, User::class, Image::class], version = 1)
abstract class BehanceDatabase : RoomDatabase() {
    abstract val behanceDao: BehanceDao
}