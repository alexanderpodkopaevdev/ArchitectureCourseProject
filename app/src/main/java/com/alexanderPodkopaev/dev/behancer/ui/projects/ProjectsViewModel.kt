package com.alexanderPodkopaev.dev.behancer.ui.projects

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexanderPodkopaev.dev.behancer.BuildConfig
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import com.alexanderPodkopaev.dev.behancer.data.model.project.ProjectResponse
import com.alexanderPodkopaev.dev.behancer.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProjectsViewModel(var mStorage: Storage?, val onItemClickListener: ProjectsAdapter.OnItemClickListener) : ViewModel() {

    var mDisposable: Disposable? = null
    var isError: MutableLiveData<Boolean> = MutableLiveData(false)
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var projects: MutableLiveData<List<Project>> = MutableLiveData()
    var onRefreshListener = SwipeRefreshLayout.OnRefreshListener { loadProjects() }


    init {
        projects.value = ArrayList()
        loadProjects()
    }


    fun loadProjects() {
        mDisposable = (ApiUtils.getApiService()
                .getProjects(BuildConfig.API_QUERY)
                .doOnSuccess { response: ProjectResponse? -> mStorage?.insertProjects(response) }
                .onErrorReturn { throwable: Throwable ->
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.javaClass)) mStorage?.getProjects() else null
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.postValue(true) }
                .doFinally { isLoading.postValue(false) }
                .subscribe(
                        { response ->
                            isError.postValue(false)
                            if (response != null)
                                projects.postValue(response.projects)
                        }
                ) {
                    isError.postValue(true)
                })

    }

    override fun onCleared() {
        mStorage = null
        mDisposable?.dispose()
    }

}