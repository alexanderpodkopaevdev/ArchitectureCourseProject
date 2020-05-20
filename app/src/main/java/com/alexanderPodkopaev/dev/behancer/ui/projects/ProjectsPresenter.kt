package com.alexanderPodkopaev.dev.behancer.ui.projects

import com.alexanderPodkopaev.dev.behancer.BuildConfig
import com.alexanderPodkopaev.dev.behancer.common.BasePresenter
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.model.project.ProjectResponse
import com.alexanderPodkopaev.dev.behancer.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState

@InjectViewState
class ProjectsPresenter(private val mStorage: Storage?) : BasePresenter<ProjectsView>() {


    fun getProjects(username: String?) {
        mCompositeDisposable
                .add(ApiUtils.getApiService()
                        .getUserProjects(username)
                        /*.doOnSuccess { response: ProjectResponse? -> mStorage?.insertProjects(response) }
                        .onErrorReturn { throwable: Throwable ->
                            if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.javaClass)) mStorage?.getProjects() else null
                        }*/
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { viewState.showRefresh() }
                        .doFinally { viewState.hideRefresh() }
                        .subscribe(
                                { response ->
                                    viewState.showProjects(response?.projects)
                                }
                        ) {
                            viewState.showError()
                        })
    }


    fun openProfileFragment(username: String?) {
        viewState.openProfileFragment(username)
    }

}