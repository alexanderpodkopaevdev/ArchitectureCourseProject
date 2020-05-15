package com.alexanderPodkopaev.dev.behancer.ui.projects

import androidx.fragment.app.Fragment
import com.alexanderPodkopaev.dev.behancer.common.SingleFragmentActivity


class ProjectsActivity : SingleFragmentActivity() {
    protected override val fragment: Fragment
        protected get() = ProjectsFragment.Companion.newInstance()
}