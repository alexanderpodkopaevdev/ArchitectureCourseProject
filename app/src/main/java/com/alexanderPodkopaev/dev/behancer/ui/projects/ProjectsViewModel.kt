package com.alexanderPodkopaev.dev.behancer.ui.projects

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexanderPodkopaev.dev.behancer.BuildConfig
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.api.BehanceApi
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import com.alexanderPodkopaev.dev.behancer.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProjectsViewModel {


    @Inject
    lateinit var mStorage: Storage

    @Inject
    lateinit var mBehancerApi: BehanceApi

    lateinit var onItemClickListener: ProjectsAdapter.OnItemClickListener

    val isLoading: ObservableBoolean = ObservableBoolean(false)
    val isError: ObservableBoolean = ObservableBoolean(false)
    val projects: ObservableArrayList<Project> = ObservableArrayList()
    val onRefreshListener: SwipeRefreshLayout.OnRefreshListener = SwipeRefreshLayout.OnRefreshListener { this.loadProjects() }
    var mCompositeDisposable = CompositeDisposable()

    fun loadProjects() {
        mCompositeDisposable.add(mBehancerApi.getProjects(BuildConfig.API_QUERY)
                .doOnSuccess { response -> mStorage.insertProjects(response) }
                .onErrorReturn { throwable ->
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.javaClass)) mStorage.getProjects() else null
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
        mCompositeDisposable.dispose()
    }

    /*   init {
           loadProjects()
       }*/

}