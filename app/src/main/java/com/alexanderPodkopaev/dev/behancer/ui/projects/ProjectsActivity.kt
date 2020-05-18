package com.alexanderPodkopaev.dev.behancer.ui.projects

import androidx.fragment.app.Fragment
import com.alexanderPodkopaev.dev.behancer.common.SingleFragmentActivity
import com.alexanderPodkopaev.dev.behancer.ui.profile.ProfileActivity.Companion.USERNAME_KEY
import com.alexanderPodkopaev.dev.behancer.ui.projects.ProjectsFragment.Companion.PROJECT_KEY


class ProjectsActivity : SingleFragmentActivity() {
    override val fragment: Fragment
        get() = ProjectsFragment.newInstance()
}