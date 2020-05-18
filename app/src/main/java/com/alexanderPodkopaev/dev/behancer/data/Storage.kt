package com.alexanderPodkopaev.dev.behancer.data

import androidx.core.util.Pair
import com.alexanderPodkopaev.dev.behancer.data.database.BehanceDao
import com.alexanderPodkopaev.dev.behancer.data.model.project.Cover
import com.alexanderPodkopaev.dev.behancer.data.model.project.Owner
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import com.alexanderPodkopaev.dev.behancer.data.model.project.ProjectResponse
import com.alexanderPodkopaev.dev.behancer.data.model.user.UserResponse
import java.util.*


class Storage(private val mBehanceDao: BehanceDao) {
    fun insertProjects(response: ProjectResponse) {
        val projects = response.projects
        mBehanceDao.insertProjects(projects)
        val assembled = assemble(projects)
        mBehanceDao.clearCoverTable()
        mBehanceDao.insertCovers(assembled.first!!)
        mBehanceDao.clearOwnerTable()
        mBehanceDao.insertOwners(assembled.second!!)
    }

    private fun assemble(projects: List<Project>): Pair<List<Cover>, List<Owner>> {
        val covers: MutableList<Cover> = ArrayList()
        val owners: MutableList<Owner> = ArrayList()
        for (i in projects.indices) {
            val cover = projects[i].cover
            cover.id = i
            cover.projectId = projects[i].id
            covers.add(cover)
            val owner = projects[i].owners[0]
            owner.id = i
            owner.projectId = projects[i].id
            owners.add(owner)
        }
        return Pair(covers, owners)
    }

    fun getProjects(): ProjectResponse {
            val projects = mBehanceDao.projects
            for (project in projects) {
                project.setCover(mBehanceDao.getCoverFromProject(project.id))
                project.setOwners(mBehanceDao.getOwnersFromProject(project.id))
            }
            val response = ProjectResponse()
            response.projects = projects
            return response
        }

    fun insertUser(response: UserResponse) {
        val user = response.user
        val image = user.image
        image.id = user.id
        image.userId = user.id
        mBehanceDao.insertUser(user)
        mBehanceDao.insertImage(image)
    }

    fun getUser(username: String): UserResponse {
        val user = mBehanceDao.getUserByName(username)
        val image = mBehanceDao.getImageFromUser(user.id)
        user.setImage(image)
        val response = UserResponse()
        response.user = user
        return response
    }

    interface StorageOwner {
        fun obtainStorage(): Storage
    }

}