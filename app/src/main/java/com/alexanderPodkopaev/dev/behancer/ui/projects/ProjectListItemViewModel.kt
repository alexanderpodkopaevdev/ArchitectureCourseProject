package com.alexanderPodkopaev.dev.behancer.ui.projects

import com.alexanderPodkopaev.dev.behancer.data.model.project.RichProject
import com.alexanderPodkopaev.dev.behancer.utils.DateUtils

class ProjectListItemViewModel(item: RichProject) {
    val imageUrl: String?
    val name: String?
    val username: String?
    val publishedOn: String?

    init {
        imageUrl = item.mProject.cover.photoUrl
        name = item.mProject.name
        publishedOn = DateUtils.format(item.mProject.publishedOn)
        username = if (!item.mOwners.isNullOrEmpty())
            item.mOwners[FIRST_OWNER_INDEX].username
        else ""
    }

    companion object {
        private const val FIRST_OWNER_INDEX = 0
    }

}