package com.alexanderPodkopaev.dev.behancer.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.model.user.User
import com.alexanderPodkopaev.dev.behancer.utils.ApiUtils
import com.alexanderPodkopaev.dev.behancer.utils.DateUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(var mStorage: Storage?, val mUsername: String?, mOnBtnClick: BtnClick) : ViewModel() {
    var mDisposable: Disposable? = null
    var isError: MutableLiveData<Boolean> = MutableLiveData(false)
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    var imageUrl: MutableLiveData<String> = MutableLiveData()
    var name: MutableLiveData<String> = MutableLiveData()
    var createdOn: MutableLiveData<String> = MutableLiveData()
    var location: MutableLiveData<String> = MutableLiveData()
    var onRefreshListener = SwipeRefreshLayout.OnRefreshListener { loadProfile() }
    var onBtnClick: BtnClick = mOnBtnClick

    init {
        loadProfile()
    }


    fun loadProfile() {
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
                .doOnSubscribe { isLoading.postValue(true) }
                .doFinally { isLoading.postValue(false) }
                .subscribe(
                        { response ->
                            isError.postValue(false)
                            if (response != null)
                                bind(response.user)
                        }
                ) {
                    isError.postValue(true)
                }

    }

    private fun bind(user: User) {
        imageUrl.postValue(user.image.photoUrl)
        name.postValue(user.displayName)
        createdOn.postValue(DateUtils.format(user.createdOn ?: 0L))
        location.postValue(user.location)

    }


    override fun onCleared() {
        mStorage = null
        mDisposable?.dispose()
    }

    interface BtnClick {
        fun openProject()

    }

}