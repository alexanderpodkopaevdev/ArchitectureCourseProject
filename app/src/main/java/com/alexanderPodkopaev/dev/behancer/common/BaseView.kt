package com.alexanderPodkopaev.dev.behancer.common

import moxy.MvpView

interface BaseView : MvpView {

    fun showRefresh()

    fun hideRefresh()

    fun showError()
}