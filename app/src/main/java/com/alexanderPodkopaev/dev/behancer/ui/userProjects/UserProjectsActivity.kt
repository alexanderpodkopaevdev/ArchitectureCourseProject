package com.alexanderPodkopaev.dev.behancer.ui.userProjects

import androidx.fragment.app.Fragment
import com.alexanderPodkopaev.dev.behancer.AppDelegate
import com.alexanderPodkopaev.dev.behancer.common.SingleFragmentActivity
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.ui.profile.ProfileActivity.Companion.USERNAME_KEY


class UserProjectsActivity : SingleFragmentActivity(), Storage.StorageOwner {
    override val fragment: Fragment
        get() = UserProjectsFragment.newInstance(intent.getBundleExtra(USERNAME_KEY))

    override fun obtainStorage(): Storage {
        return (applicationContext as AppDelegate).storage
    }
}
