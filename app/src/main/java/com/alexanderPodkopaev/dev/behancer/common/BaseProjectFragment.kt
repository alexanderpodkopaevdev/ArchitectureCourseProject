package com.alexanderPodkopaev.dev.behancer.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.databinding.ProjectsBinding

abstract class BaseProjectsFragment : Fragment() {

    open val viewModelClass: Class<out BaseViewModel> = BaseViewModel::class.java

    lateinit var mBaseViewModel: BaseViewModel
    lateinit var mStorage: Storage



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Storage.StorageOwner) {
            mStorage = (context as Storage.StorageOwner).obtainStorage()
            val factory = getCustomFactory()
            mBaseViewModel = ViewModelProvider(this, factory).get(viewModelClass)
        }
    }

    abstract fun getCustomFactory(): ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ProjectsBinding.inflate(inflater, container, false)
        binding.vm = mBaseViewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}



