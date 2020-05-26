package com.alexanderPodkopaev.dev.behancer.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.model.project.ProjectResponse
import com.alexanderPodkopaev.dev.behancer.data.model.project.RichProject
import com.alexanderPodkopaev.dev.behancer.ui.projects.ProjectsAdapter
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


abstract class BaseViewModel(var mStorage: Storage?, var onItemClickListener: ProjectsAdapter.OnItemClickListener) : ViewModel() {


    var mDisposable: Disposable? = null
    var isError: MutableLiveData<Boolean> = MutableLiveData(false)
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var projects: LiveData<List<RichProject>>? = null
    var onRefreshListener = SwipeRefreshLayout.OnRefreshListener { updateProjects() }

    fun updateProjects() {
        mDisposable = (getProject()
                .map(ProjectResponse::projects)
                .doOnSuccess { isError.postValue(false) }
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { isLoading.postValue(true) }
                .doFinally { isLoading.postValue(false) }
                .subscribe(
                        { response ->
                            mStorage?.insertProjects(response)
                        }
                ) {
/*
                    mStorage?.getProjects()*/
                    val value = projects?.value == null || projects?.value?.size == 0
                    isError.postValue(value)
                })
    }

    abstract fun getProject(): Single<ProjectResponse?>

    override fun onCleared() {
        mStorage = null
        mDisposable?.dispose()
    }

}