package com.alexanderPodkopaev.dev.behancer.ui.profile

import com.alexanderPodkopaev.dev.behancer.common.BasePresenter
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfilePresenter(private val mView: ProfileView, private val mStorage: Storage?) : BasePresenter() {

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
                .doOnSubscribe { mView.showRefresh() }
                .doFinally { mView.hideRefresh() }
                .subscribe(
                        { response ->
                            mView.showProfile(response?.user)
                        }
                ) {
                    mView.showError()
                })

    }

}