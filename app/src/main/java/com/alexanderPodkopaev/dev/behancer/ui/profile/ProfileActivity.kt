package com.alexanderPodkopaev.dev.behancer.ui.profile

import androidx.fragment.app.Fragment
import com.alexanderPodkopaev.dev.behancer.AppDelegate
import com.alexanderPodkopaev.dev.behancer.common.SingleFragmentActivity
import com.alexanderPodkopaev.dev.behancer.data.Storage


class ProfileActivity : SingleFragmentActivity(), Storage.StorageOwner {
    protected override val fragment: Fragment
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