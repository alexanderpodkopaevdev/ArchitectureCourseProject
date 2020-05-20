package com.alexanderPodkopaev.dev.behancer.ui.projects

import androidx.fragment.app.Fragment
import com.alexanderPodkopaev.dev.behancer.AppDelegate
import com.alexanderPodkopaev.dev.behancer.common.SingleFragmentActivity
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.ui.profile.ProfileActivity.Companion.USERNAME_KEY
import com.alexanderPodkopaev.dev.behancer.ui.projects.ProjectsFragment.Companion.PROJECT_KEY


class ProjectsActivity : SingleFragmentActivity(), Storage.StorageOwner {
    override val fragment: Fragment
        get() = ProjectsFragment.newInstance()

        override fun obtainStorage(): Storage {
        return (applicationContext as AppDelegate).storage
    }
}