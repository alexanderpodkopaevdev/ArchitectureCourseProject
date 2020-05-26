package com.alexanderPodkopaev.dev.behancer.ui.userProjects

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alexanderPodkopaev.dev.behancer.common.BaseProjectsFragment
import com.alexanderPodkopaev.dev.behancer.ui.profile.ProfileActivity
import com.alexanderPodkopaev.dev.behancer.ui.projects.ProjectsAdapter
import com.alexanderPodkopaev.dev.behancer.utils.CustomFactoryUserProjects


class UserProjectsFragment : BaseProjectsFragment() {
    override val viewModelClass = UserProjectViewModel::class.java

    private var mUsername: String? = null
    var mOnItemClickListener = object : ProjectsAdapter.OnItemClickListener {
        override fun onItemClick(username: String?) {
        }
    }

    override fun getCustomFactory(): ViewModelProvider.Factory {
        mUsername = arguments?.getString(ProfileActivity.USERNAME_KEY)
        return CustomFactoryUserProjects(mStorage, mUsername, mOnItemClickListener)
    }


    companion object {
        const val PROJECT_KEY = "PROJECT_KEY"
        fun newInstance(args: Bundle?): UserProjectsFragment {
            val fragment = UserProjectsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
