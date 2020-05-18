package com.alexanderPodkopaev.dev.behancer.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.alexanderPodkopaev.dev.behancer.R
import com.alexanderPodkopaev.dev.behancer.common.PresenterFragment
import com.alexanderPodkopaev.dev.behancer.common.RefreshOwner
import com.alexanderPodkopaev.dev.behancer.common.Refreshable
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.Storage.StorageOwner
import com.alexanderPodkopaev.dev.behancer.data.model.user.User
import com.alexanderPodkopaev.dev.behancer.utils.DateUtils
import com.squareup.picasso.Picasso


class ProfileFragment : PresenterFragment<ProfilePresenter>(), ProfileView, Refreshable {
    private var mRefreshOwner: RefreshOwner? = null
    lateinit var mErrorView: View
    lateinit var mProfileView: View
    private var mUsername: String? = null
    private var mStorage: Storage? = null
    lateinit var mPresenter: ProfilePresenter
    lateinit var mProfileImage: ImageView
    lateinit var mProfileName: TextView
    lateinit var mProfileCreatedOn: TextView
    lateinit var mProfileLocation: TextView
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mStorage = if (context is StorageOwner) (context as StorageOwner).obtainStorage() else null
        mRefreshOwner = if (context is RefreshOwner) context else null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mErrorView = view.findViewById(R.id.errorView)
        mProfileView = view.findViewById(R.id.view_profile)
        mProfileImage = view.findViewById(R.id.iv_profile)
        mProfileName = view.findViewById(R.id.tv_display_name_details)
        mProfileCreatedOn = view.findViewById(R.id.tv_created_on_details)
        mProfileLocation = view.findViewById(R.id.tv_location_details)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mUsername = arguments?.getString(PROFILE_KEY)
        activity?.title = mUsername
        mPresenter = ProfilePresenter(this, mStorage)
        mProfileView.visibility = View.VISIBLE
        onRefreshData()
    }

    override fun onRefreshData() {
        mPresenter.getProfile(mUsername)
    }


    private fun bind(user: User?) {
        Picasso.get()
                .load(user?.image?.photoUrl)
                .fit()
                .into(mProfileImage)
        mProfileName.text = user?.displayName
        mProfileCreatedOn.text = DateUtils.format(user?.createdOn ?: 0L)
        mProfileLocation.text = user?.location

    }

    override fun onDetach() {
        mStorage = null
        mRefreshOwner = null
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

    override fun getPresenter(): ProfilePresenter? {
        return mPresenter
    }

    override fun showProfile(user: User?) {
        mErrorView.visibility = View.GONE
        mProfileView.visibility = View.VISIBLE
        bind(user)
    }

    override fun showRefresh() {
        mRefreshOwner?.setRefreshState(true)
    }

    override fun hideRefresh() {
        mRefreshOwner?.setRefreshState(false)
    }

    override fun showError() {
        mErrorView.visibility = View.VISIBLE
        mProfileView.visibility = View.GONE
    }
}