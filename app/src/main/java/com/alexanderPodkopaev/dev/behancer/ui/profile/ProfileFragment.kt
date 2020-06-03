package com.alexanderPodkopaev.dev.behancer.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexanderPodkopaev.dev.behancer.AppDelegate
import com.alexanderPodkopaev.dev.behancer.databinding.ProfilesBinding
import toothpick.Toothpick
import javax.inject.Inject


class ProfileFragment : Fragment() {

    @Inject
    lateinit var mProfileViewModel: ProfileViewModel
    private var mUsername: String? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Toothpick.inject(this, AppDelegate.sAppScope)

        mUsername = arguments?.getString(PROFILE_KEY)
        mProfileViewModel.mUsername = mUsername
        mProfileViewModel.getProfile()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ProfilesBinding.inflate(inflater, container, false)
        binding.vm = mProfileViewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.title = mUsername
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