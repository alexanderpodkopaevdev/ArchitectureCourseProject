package com.alexanderPodkopaev.dev.behancer.ui.profile

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.model.user.User
import com.alexanderPodkopaev.dev.behancer.utils.ApiUtils
import com.alexanderPodkopaev.dev.behancer.utils.DateUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(var mStorage: Storage?) {
    var mUsername: String? = null
    var mDisposable: Disposable? = null
    var isError: ObservableBoolean = ObservableBoolean(false)
    var isLoading: ObservableBoolean = ObservableBoolean(false)

    var imageUrl: ObservableField<String> = ObservableField()
    var name: ObservableField<String> = ObservableField()
    var createdOn: ObservableField<String> = ObservableField()
    var location: ObservableField<String> = ObservableField()

    var onRefreshListener = SwipeRefreshLayout.OnRefreshListener { loadProfile(mUsername) }

    fun loadProfile(username: String?) {
        mUsername = username
        mDisposable = ApiUtils.getApiService()
                .getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess { response -> mStorage?.insertUser(response) }
                .onErrorReturn { throwable: Throwable ->
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.javaClass)) {
                        mStorage?.getUser(mUsername)
                    } else null
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.set(true) }
                .doFinally { isLoading.set(false) }
                .subscribe(
                        { response ->
                            isError.set(false)
                            if (response != null)
                                bind(response.user)
                        }
                ) {
                    isError.set(true)
                }

    }

    private fun bind(user: User) {
        imageUrl.set(user.image.photoUrl)
        name.set(user.displayName)
        createdOn.set(DateUtils.format(user.createdOn ?: 0L))
        location.set(user.location)
    }


    fun dispatchDetach() {
        mStorage = null
        mDisposable?.dispose()
    }

}