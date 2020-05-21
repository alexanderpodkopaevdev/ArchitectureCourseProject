package com.alexanderPodkopaev.dev.behancer.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.Storage.StorageOwner
import com.alexanderPodkopaev.dev.behancer.data.model.user.User
import com.alexanderPodkopaev.dev.behancer.databinding.ProfileBinding
import com.alexanderPodkopaev.dev.behancer.databinding.ProfilesBinding


class ProfileFragment : Fragment() {

    lateinit var mProfileViewModel: ProfileViewModel
    private var mUsername: String? = null
    private var mStorage: Storage? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mStorage = if (context is StorageOwner) (context as StorageOwner).obtainStorage() else null
        mProfileViewModel = ProfileViewModel(mStorage)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ProfilesBinding.inflate(inflater, container, false)
        binding.vm = mProfileViewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mUsername = arguments?.getString(PROFILE_KEY)
        activity?.title = mUsername
        mProfileViewModel.loadProfile(mUsername)
    }


    override fun onDetach() {
        mProfileViewModel.dispatchDetach()
        super.onDetach()
    }

    companion object {
        const val PROFILE_KEY = "PROFILE_KEY"
        fun newInstance(args: Bundle?): ProfileFragment {
            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }
}