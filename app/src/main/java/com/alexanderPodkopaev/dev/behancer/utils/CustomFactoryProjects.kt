package com.alexanderPodkopaev.dev.behancer.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.ui.profile.ProfileViewModel
import com.alexanderPodkopaev.dev.behancer.ui.projects.ProjectsAdapter
import com.alexanderPodkopaev.dev.behancer.ui.projects.ProjectsViewModel

class CustomFactoryProjects(val storage: Storage, val onItemClickListener: ProjectsAdapter.OnItemClickListener) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProjectsViewModel(storage,onItemClickListener) as T
    }
}

class CustomFactoryProfile(val storage: Storage, val mUsername: String?) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(storage, mUsername) as T
    }
}