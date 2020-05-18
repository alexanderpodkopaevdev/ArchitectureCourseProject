package com.alexanderPodkopaev.dev.behancer.ui.profile

import androidx.fragment.app.Fragment
import com.alexanderPodkopaev.dev.behancer.common.SingleFragmentActivity


class ProfileActivity : SingleFragmentActivity() {
    protected override val fragment: Fragment
        get() {
            if (intent != null) {
                return ProfileFragment.Companion.newInstance(intent.getBundleExtra(USERNAME_KEY))
            }
            throw IllegalStateException("getIntent cannot be null")
        }

    companion object {
        const val USERNAME_KEY = "USERNAME_KEY"
    }
}