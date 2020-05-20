package com.alexanderPodkopaev.dev.behancer.ui.profile

import com.alexanderPodkopaev.dev.behancer.AppDelegate
import com.alexanderPodkopaev.dev.behancer.common.RefreshActivity
import com.alexanderPodkopaev.dev.behancer.data.Storage
import moxy.MvpAppCompatFragment

class ProfileActivity : RefreshActivity(), Storage.StorageOwner {
    protected override val fragment: MvpAppCompatFragment
        get() {
            if (intent != null) {
                return ProfileFragment.newInstance(intent.getBundleExtra(USERNAME_KEY))
            }
            throw IllegalStateException("getIntent cannot be null")
        }

    override fun obtainStorage(): Storage {
        return (applicationContext as AppDelegate).storage
    }

    companion object {
        const val USERNAME_KEY = "USERNAME_KEY"
    }
}