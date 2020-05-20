package com.alexanderPodkopaev.dev.behancer.ui.projects

import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import com.alexanderPodkopaev.dev.behancer.utils.DateUtils

class ProjectListItemViewModel(project: Project) {
    val imageUrl: String?
    val name: String?
    val username: String?
    val publishedOn: String?

    init {
        imageUrl = project.cover.photoUrl
        name = project.name
        username = project.owners[FIRST_OWNER_INDEX].username
        publishedOn = DateUtils.format(project.publishedOn)
    }

    companion object {
        private const val FIRST_OWNER_INDEX = 0
    }

}