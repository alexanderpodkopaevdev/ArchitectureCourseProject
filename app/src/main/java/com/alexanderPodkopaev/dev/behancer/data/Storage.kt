package com.alexanderPodkopaev.dev.behancer.data

import androidx.lifecycle.LiveData
import com.alexanderPodkopaev.dev.behancer.data.database.BehanceDao
import com.alexanderPodkopaev.dev.behancer.data.model.project.Owner
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import com.alexanderPodkopaev.dev.behancer.data.model.project.ProjectResponse
import com.alexanderPodkopaev.dev.behancer.data.model.project.RichProject
import com.alexanderPodkopaev.dev.behancer.data.model.user.User
import com.alexanderPodkopaev.dev.behancer.data.model.user.UserResponse
import java.util.*


class Storage(private val mBehanceDao: BehanceDao) {
    fun insertProjects(response: ProjectResponse?) {
        response?.projects?.let { insertProjects(it) }

    }

    fun insertProjects(projects: List<Project>) {
        mBehanceDao.insertProjects(projects)
        val owners = getOwners(projects)
        mBehanceDao.clearOwnerTable()
        mBehanceDao.insertOwners(owners)
    }

    private fun getOwners(projects: List<Project>): List<Owner> {
        val owners: MutableList<Owner> = ArrayList()
        for (i in projects.indices) {
            val owner = projects[i].owners[0]
            owner.id = i
            owner.projectId = projects[i].id
            owners.add(owner)
        }
        return (owners)
    }

    fun getProjectsLive(): LiveData<List<RichProject>> {
        return mBehanceDao.getProjectsLive()
    }

    fun getProjects(): ProjectResponse {
        val projects = mBehanceDao.projects
        for (project in projects) {
            project.setOwners(mBehanceDao.getOwnersFromProject(project.id))
        }
        val response = ProjectResponse()
        response.projects = projects
        return response
    }

    fun insertUser(response: UserResponse?) {
        response?.let {
            val user = it.user
            val image = user.image
            image.id = user.id
            image.userId = user.id
            mBehanceDao.insertUser(user)
            mBehanceDao.insertImage(image)
        }
    }

    fun getUserLive(): LiveData<List<User>> {
        return mBehanceDao.getUsersLive()
    }

    fun getUser(username: String?): UserResponse {
        val response = UserResponse()
        username?.let {
            val user = mBehanceDao.getUserByName(username)
            val image = mBehanceDao.getImageFromUser(user.id)
            user.setImage(image)
            response.user = user
        }
        return response
    }

    interface StorageOwner {
        fun obtainStorage(): Storage
    }

}