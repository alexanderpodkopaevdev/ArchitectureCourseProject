package com.alexanderPodkopaev.dev.behancer.ui.projects

import android.view.View
import com.alexanderPodkopaev.dev.behancer.BuildConfig
import com.alexanderPodkopaev.dev.behancer.common.BasePresenter
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.model.project.ProjectResponse
import com.alexanderPodkopaev.dev.behancer.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProjectsPresenter(private val mView: ProjectsView, private val mStorage: Storage?) : BasePresenter() {

    fun getProjects() {
        mCompositeDisposable
                .add(ApiUtils.getApiService()
                .getProjects(BuildConfig.API_QUERY)
                .doOnSuccess { response: ProjectResponse? -> mStorage?.insertProjects(response)  }
                .onErrorReturn { throwable: Throwable ->
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.javaClass)) mStorage?.getProjects() else null
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mView.showRefresh() }
                .doFinally { mView.hideRefresh() }
                .subscribe(
                        {response ->
                            mView.showProjects(response?.projects)
                        }
                ) {
                    mView.showError()
                })
    }

    fun openProfileFragment(username: String?) {
        mView.openProfileFragment(username)
    }
}