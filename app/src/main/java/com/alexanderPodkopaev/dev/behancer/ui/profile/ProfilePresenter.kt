package com.alexanderPodkopaev.dev.behancer.ui.profile

import com.alexanderPodkopaev.dev.behancer.common.BasePresenter
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState

@InjectViewState
class ProfilePresenter(private val mStorage: Storage?) : BasePresenter<ProfileView>() {

    fun getProfile(mUsername: String?) {

        mCompositeDisposable.add(ApiUtils.getApiService()
                .getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess { response -> mStorage?.insertUser(response) }
                .onErrorReturn { throwable: Throwable ->
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.javaClass)) {
                        mStorage?.getUser(mUsername)
                    } else null
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showRefresh() }
                .doFinally { viewState.hideRefresh() }
                .subscribe(
                        { response ->
                            viewState.showProfile(response?.user)
                        }
                ) {
                    viewState.showError()
                })

    }

    fun openUserProject(username: String?) {
        viewState.openUserProject(username)
    }

}