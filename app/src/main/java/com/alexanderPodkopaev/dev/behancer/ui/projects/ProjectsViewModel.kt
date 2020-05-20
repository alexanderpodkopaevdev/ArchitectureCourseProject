package com.alexanderPodkopaev.dev.behancer.ui.projects

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import com.alexanderPodkopaev.dev.behancer.BuildConfig
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import com.alexanderPodkopaev.dev.behancer.data.model.project.ProjectResponse
import com.alexanderPodkopaev.dev.behancer.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProjectsViewModel(var mStorage: Storage?, val mOnItemClickListener: ProjectsAdapter.OnItemClickListener) {

    var mDisposable: Disposable? = null
    var mIsError: ObservableBoolean = ObservableBoolean(false)
    var mIsLoading: ObservableBoolean = ObservableBoolean(false)
    var mProjects: ObservableList<Project> = ObservableArrayList()


    fun loadProjects() {
        mDisposable = (ApiUtils.getApiService()
                .getProjects(BuildConfig.API_QUERY)
                .doOnSuccess { response: ProjectResponse? -> mStorage?.insertProjects(response) }
                .onErrorReturn { throwable: Throwable ->
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.javaClass)) mStorage?.getProjects() else null
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mIsLoading.set(true) }
                .doFinally { mIsLoading.set(false) }
                .subscribe(
                        { response ->
                            mIsError.set(false)
                            if (response != null)
                                mProjects.addAll(response.projects)
                        }
                ) {
                    mIsError.set(true)
                })

    }

    fun dispatchDetach() {
        mStorage = null
        mDisposable?.dispose()
    }

}