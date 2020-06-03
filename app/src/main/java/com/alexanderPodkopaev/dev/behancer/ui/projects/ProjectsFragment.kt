package com.alexanderPodkopaev.dev.behancer.ui.projects

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexanderPodkopaev.dev.behancer.AppDelegate
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.databinding.ProjectsBinding
import com.alexanderPodkopaev.dev.behancer.ui.profile.ProfileActivity
import com.alexanderPodkopaev.dev.behancer.ui.profile.ProfileFragment
import toothpick.Toothpick
import javax.inject.Inject


class ProjectsFragment : Fragment() {

    @Inject
    lateinit var mProjectsViewModel: ProjectsViewModel

    var mOnItemClickListener : ProjectsAdapter.OnItemClickListener = object : ProjectsAdapter.OnItemClickListener {
        override fun onItemClick(username: String?) {
            val intent = Intent(activity, ProfileActivity::class.java)
            val args = Bundle()
            args.putString(ProfileFragment.PROFILE_KEY, username)
            intent.putExtra(ProfileActivity.USERNAME_KEY, args)
            startActivity(intent)
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ProjectsBinding.inflate(inflater, container, false)
        binding.vm = mProjectsViewModel
        return binding.root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Toothpick.inject(this, AppDelegate.sAppScope)
        mProjectsViewModel.onItemClickListener = mOnItemClickListener
        mProjectsViewModel.loadProjects()
    }



    companion object {
        fun newInstance(): ProjectsFragment {
            return ProjectsFragment()
        }
    }

    override fun onDetach() {
        mProjectsViewModel.dispatchDetach()
        super.onDetach()
    }

}