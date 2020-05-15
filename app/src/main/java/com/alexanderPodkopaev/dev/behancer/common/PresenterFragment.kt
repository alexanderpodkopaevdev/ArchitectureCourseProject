package com.alexanderPodkopaev.dev.behancer.common

import androidx.fragment.app.Fragment

abstract class PresenterFragment<P : BasePresenter> : Fragment() {

    abstract fun getPresenter(): P?

    override fun onDetach() {
        getPresenter()?.disposeAll()
        super.onDetach()
    }
}