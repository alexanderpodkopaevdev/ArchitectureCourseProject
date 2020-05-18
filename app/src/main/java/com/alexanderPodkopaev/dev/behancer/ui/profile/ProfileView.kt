package com.alexanderPodkopaev.dev.behancer.ui.profile

import com.alexanderPodkopaev.dev.behancer.common.BaseView
import com.alexanderPodkopaev.dev.behancer.data.model.user.User

interface ProfileView : BaseView {
    fun showProfile(user: User?)
    fun openUserProject(username: String?)
}