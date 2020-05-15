package com.alexanderPodkopaev.dev.behancer.common

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter {

    val mCompositeDisposable = CompositeDisposable()

    fun disposeAll() {
        mCompositeDisposable.clear()
    }
}