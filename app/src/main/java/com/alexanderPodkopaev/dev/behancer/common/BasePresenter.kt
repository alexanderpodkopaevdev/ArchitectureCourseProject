package com.alexanderPodkopaev.dev.behancer.common

import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter

abstract class BasePresenter<View : BaseView> : MvpPresenter<View>() {

    val mCompositeDisposable = CompositeDisposable()

    fun disposeAll() {
        mCompositeDisposable.dispose()
    }
}