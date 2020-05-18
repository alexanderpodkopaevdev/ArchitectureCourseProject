package com.alexanderPodkopaev.dev.behancer.ui.projects

import com.alexanderPodkopaev.dev.behancer.common.BaseView
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project

interface ProjectsView : BaseView {

    fun showProjects(projects: List<Project>?)
    fun openProfileFragment(username: String?)
}