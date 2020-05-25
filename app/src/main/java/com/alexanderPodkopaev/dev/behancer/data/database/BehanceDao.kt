package com.alexanderPodkopaev.dev.behancer.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexanderPodkopaev.dev.behancer.data.model.project.Cover
import com.alexanderPodkopaev.dev.behancer.data.model.project.Owner
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import com.alexanderPodkopaev.dev.behancer.data.model.project.RichProject
import com.alexanderPodkopaev.dev.behancer.data.model.user.Image
import com.alexanderPodkopaev.dev.behancer.data.model.user.User


@Dao
interface BehanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProjects(projects: List<Project>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCovers(covers: List<Cover>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOwners(owners: List<Owner>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(image: Image)

    @get:Query("select * from project")
    val projects: List<Project>

    @Query("select * from project")
    fun getProjectsLive() : LiveData<List<RichProject>>

    @Query("select * from owner where project_id = :projectId")
    fun getOwnersFromProject(projectId: Int): List<Owner>

    @Query("select * from user where username = :userName")
    fun getUserByName(userName: String): User

    @Query("select * from image where user_id = :userId")
    fun getImageFromUser(userId: Int): Image

    @Query("delete from owner")
    fun clearOwnerTable()

    @Query("delete from cover")
    fun clearCoverTable()

    @Query("delete from image")
    fun clearImageTable()

    @get:Query("select * from user")
    val users: List<User>

    @Query("select * from user")
    fun getUsersLive() : LiveData<List<User>>

    @get:Query("select * from image")
    val images: List<Image>
}