package com.alexanderPodkopaev.dev.behancer.common

import moxy.MvpAppCompatFragment


abstract class PresenterFragment : MvpAppCompatFragment() {

    abstract fun getPresenter(): BasePresenter<*>?

    override fun onDetach() {
        getPresenter()?.disposeAll()
        super.onDetach()
    }
}