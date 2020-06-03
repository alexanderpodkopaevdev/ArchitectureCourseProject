package com.alexanderPodkopaev.dev.behancer.ui.projects

import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import com.alexanderPodkopaev.dev.behancer.utils.DateUtils

class ProjectListItemViewModel(item: Project) {
    val imageUrl: String?
    val name: String?
    val username: String?
    val publishedOn: String?

    init {
        imageUrl = item.cover.photoUrl
        name = item.name
        publishedOn = DateUtils.format(item.publishedOn)
        username = item.owners[FIRST_OWNER_INDEX].username

    }

    companion object {
        private const val FIRST_OWNER_INDEX = 0
    }

}