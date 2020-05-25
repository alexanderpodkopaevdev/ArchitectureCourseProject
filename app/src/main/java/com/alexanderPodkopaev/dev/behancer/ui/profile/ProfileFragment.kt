package com.alexanderPodkopaev.dev.behancer.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alexanderPodkopaev.dev.behancer.data.Storage.StorageOwner
import com.alexanderPodkopaev.dev.behancer.databinding.ProfilesBinding
import com.alexanderPodkopaev.dev.behancer.utils.CustomFactoryProfile


class ProfileFragment : Fragment() {

    lateinit var mProfileViewModel: ProfileViewModel
    private var mUsername: String? = null



    override fun onAttach(context: Context) {
        super.onAttach(context)
        val mStorage = (context as StorageOwner).obtainStorage()
        mUsername = arguments?.getString(PROFILE_KEY)
        val factory = CustomFactoryProfile(mStorage, mUsername)
        mProfileViewModel = ViewModelProvider(this,factory).get(ProfileViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ProfilesBinding.inflate(inflater, container, false)
        binding.vm = mProfileViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.title = mUsername
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