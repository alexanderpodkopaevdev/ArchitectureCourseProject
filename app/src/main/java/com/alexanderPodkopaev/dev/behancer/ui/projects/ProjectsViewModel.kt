package com.alexanderPodkopaev.dev.behancer.ui.projects

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexanderPodkopaev.dev.behancer.BuildConfig
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import com.alexanderPodkopaev.dev.behancer.data.model.project.ProjectResponse
import com.alexanderPodkopaev.dev.behancer.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProjectsViewModel(var mStorage: Storage?, val onItemClickListener: ProjectsAdapter.OnItemClickListener) {

    var mDisposable: Disposable? = null
    var isError: ObservableBoolean = ObservableBoolean(false)
    var isLoading: ObservableBoolean = ObservableBoolean(false)
    var projects: ObservableList<Project> = ObservableArrayList()
    var onRefreshListener = SwipeRefreshLayout.OnRefreshListener { loadProjects() }


    fun loadProjects() {
        mDisposable = (ApiUtils.getApiService()
                .getProjects(BuildConfig.API_QUERY)
                .doOnSuccess { response: ProjectResponse? -> mStorage?.insertProjects(response) }
                .onErrorReturn { throwable: Throwable ->
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.javaClass)) mStorage?.getProjects() else null
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.set(true) }
                .doFinally { isLoading.set(false) }
                .subscribe(
                        { response ->
                            isError.set(false)
                            if (response != null)
                                projects.addAll(response.projects)
                        }
                ) {
                    isError.set(true)
                })

    }

    fun dispatchDetach() {
        mStorage = null
        mDisposable?.dispose()
    }

}