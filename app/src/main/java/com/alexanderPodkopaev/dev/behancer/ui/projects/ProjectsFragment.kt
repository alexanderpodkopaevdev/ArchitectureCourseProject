package com.alexanderPodkopaev.dev.behancer.ui.projects

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alexanderPodkopaev.dev.behancer.common.BaseProjectsFragment
import com.alexanderPodkopaev.dev.behancer.ui.profile.ProfileActivity
import com.alexanderPodkopaev.dev.behancer.ui.profile.ProfileFragment
import com.alexanderPodkopaev.dev.behancer.utils.CustomFactoryProjects


class ProjectsFragment : BaseProjectsFragment() {

    override val viewModelClass = ProjectsViewModel::class.java


    var mOnItemClickListener = object : ProjectsAdapter.OnItemClickListener {
        override fun onItemClick(username: String?) {
            val intent = Intent(activity, ProfileActivity::class.java)
            val args = Bundle()
            args.putString(ProfileFragment.PROFILE_KEY, username)
            intent.putExtra(ProfileActivity.USERNAME_KEY, args)
            startActivity(intent)
        }
    }


    override fun getCustomFactory(): ViewModelProvider.Factory {
        return CustomFactoryProjects(mStorage, mOnItemClickListener)
    }


    companion object {
        fun newInstance(): ProjectsFragment {
            return ProjectsFragment()
        }
    }

}