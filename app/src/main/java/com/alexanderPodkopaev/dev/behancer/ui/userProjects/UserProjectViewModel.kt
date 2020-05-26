package com.alexanderPodkopaev.dev.behancer.ui.userProjects

import android.util.Log
import com.alexanderPodkopaev.dev.behancer.common.BaseViewModel
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.model.project.ProjectResponse
import com.alexanderPodkopaev.dev.behancer.ui.projects.ProjectsAdapter
import com.alexanderPodkopaev.dev.behancer.utils.ApiUtils
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class UserProjectViewModel(mStorage: Storage?, var mUsername: String?, onItemClickListener: ProjectsAdapter.OnItemClickListener) : BaseViewModel(mStorage, onItemClickListener) {
    init {
        projects = mStorage?.getProjectsLive()
        updateProjects()
    }

    override fun getProject(): Single<ProjectResponse?> {
        return ApiUtils.getApiService().getUserProjects(mUsername)
    }


}