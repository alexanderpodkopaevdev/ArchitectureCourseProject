package com.alexanderPodkopaev.dev.behancer.ui.projects

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexanderPodkopaev.dev.behancer.BuildConfig
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.model.project.ProjectResponse
import com.alexanderPodkopaev.dev.behancer.data.model.project.RichProject
import com.alexanderPodkopaev.dev.behancer.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProjectsViewModel(var mStorage: Storage?, val onItemClickListener: ProjectsAdapter.OnItemClickListener) : ViewModel() {

    var mDisposable: Disposable? = null
    var isError: MutableLiveData<Boolean> = MutableLiveData(false)
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var projects: LiveData<List<RichProject>>? = mStorage?.getProjectsLive()
    var onRefreshListener = SwipeRefreshLayout.OnRefreshListener { updateProjects() }


    init {
        updateProjects()
    }

    fun updateProjects() {
        mDisposable = (ApiUtils.getApiService()
                .getProjects(BuildConfig.API_QUERY)
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
                    mStorage?.getProjects()
                    isError.postValue(projects?.value.isNullOrEmpty())


                })
    }

    override fun onCleared() {
        mStorage = null
        mDisposable?.dispose()
    }

}