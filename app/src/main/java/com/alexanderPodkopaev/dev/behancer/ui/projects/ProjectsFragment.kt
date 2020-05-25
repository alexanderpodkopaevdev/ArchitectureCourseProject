package com.alexanderPodkopaev.dev.behancer.ui.projects

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alexanderPodkopaev.dev.behancer.data.Storage.StorageOwner
import com.alexanderPodkopaev.dev.behancer.databinding.ProjectsBinding
import com.alexanderPodkopaev.dev.behancer.ui.profile.ProfileActivity
import com.alexanderPodkopaev.dev.behancer.ui.profile.ProfileFragment
import com.alexanderPodkopaev.dev.behancer.utils.CustomFactoryProjects


class ProjectsFragment : Fragment() {

    lateinit var mProjectsViewModel: ProjectsViewModel
    private var mUsername: String? = null
    var mOnItemClickListener = object : ProjectsAdapter.OnItemClickListener {
        override fun onItemClick(username: String?) {
            val intent = Intent(activity, ProfileActivity::class.java)
            val args = Bundle()
            args.putString(ProfileFragment.PROFILE_KEY, username)
            intent.putExtra(ProfileActivity.USERNAME_KEY, args)
            startActivity(intent)
        }

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is StorageOwner) {
            val mStorage = (context as StorageOwner).obtainStorage()
            val factory = CustomFactoryProjects(mStorage,mOnItemClickListener)
            mProjectsViewModel = ViewModelProvider(this,factory).get(ProjectsViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ProjectsBinding.inflate(inflater, container, false)
        binding.vm = mProjectsViewModel
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mUsername = arguments?.getString(PROJECT_KEY)

    }

    companion object {
        const val PROJECT_KEY = "PROJECT_KEY"
        fun newInstance(): ProjectsFragment {
            return ProjectsFragment()

        }
    }

}