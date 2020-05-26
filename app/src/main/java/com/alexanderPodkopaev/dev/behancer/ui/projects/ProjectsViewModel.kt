package com.alexanderPodkopaev.dev.behancer.ui.projects

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexanderPodkopaev.dev.behancer.BuildConfig
import com.alexanderPodkopaev.dev.behancer.common.BaseViewModel
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.model.project.ProjectResponse
import com.alexanderPodkopaev.dev.behancer.data.model.project.RichProject
import com.alexanderPodkopaev.dev.behancer.utils.ApiUtils
import io.reactivex.Single
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

class ProjectsViewModel(mStorage: Storage?, onItemClickListener: ProjectsAdapter.OnItemClickListener) : BaseViewModel(mStorage, onItemClickListener) {

    init {
        projects = mStorage?.getProjectsLive()
        updateProjects()
    }

    override fun getProject(): Single<ProjectResponse?> {
        return ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY)
    }

}