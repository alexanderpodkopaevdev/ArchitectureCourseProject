package com.alexanderPodkopaev.dev.behancer.ui.profile

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.api.BehanceApi
import com.alexanderPodkopaev.dev.behancer.data.model.user.User
import com.alexanderPodkopaev.dev.behancer.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel {

    var mCompositeDisposable = CompositeDisposable()


    @Inject
    lateinit var mStorage: Storage

    @Inject
    lateinit var mBehancerApi: BehanceApi

    var mUsername: String? = null

    var isLoading: ObservableBoolean = ObservableBoolean(false)
    var isError: ObservableBoolean = ObservableBoolean(false)
    var onRefreshListener: SwipeRefreshLayout.OnRefreshListener = SwipeRefreshLayout.OnRefreshListener { this.getProfile() }
    var user: ObservableField<User> = ObservableField()


    fun getProfile() {
        mCompositeDisposable.add(mBehancerApi
                .getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess { response -> mStorage.insertUser(response) }
                .onErrorReturn { throwable: Throwable ->
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.javaClass)) {
                        mStorage.getUser(mUsername)
                    } else null
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.set(true) }
                .doFinally { isLoading.set(false) }
                .subscribe(
                        { response ->
                            isError.set(false)
                            if (response != null)
                                user.set(response.user)
                        }
                ) {
                    isError.set(true)
                }
        )

    }

    fun dispatchDetach() {
        mCompositeDisposable.dispose()
    }

}