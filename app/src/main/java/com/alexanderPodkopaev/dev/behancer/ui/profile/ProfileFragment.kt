package com.alexanderPodkopaev.dev.behancer.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alexanderPodkopaev.dev.behancer.R
import com.alexanderPodkopaev.dev.behancer.common.RefreshOwner
import com.alexanderPodkopaev.dev.behancer.common.Refreshable
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.Storage.StorageOwner
import com.alexanderPodkopaev.dev.behancer.data.model.user.User
import com.alexanderPodkopaev.dev.behancer.data.model.user.UserResponse
import com.alexanderPodkopaev.dev.behancer.utils.ApiUtils
import com.alexanderPodkopaev.dev.behancer.utils.DateUtils
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class ProfileFragment : Fragment(), Refreshable {
    private var mRefreshOwner: RefreshOwner? = null
    private var mErrorView: View? = null
    private var mProfileView: View? = null
    private var mUsername: String? = null
    private var mStorage: Storage? = null
    private var mDisposable: Disposable? = null
    private var mProfileImage: ImageView? = null
    private var mProfileName: TextView? = null
    private var mProfileCreatedOn: TextView? = null
    private var mProfileLocation: TextView? = null
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
        if (arguments != null) {
            mUsername = arguments!!.getString(PROFILE_KEY)
        }
        if (activity != null) {
            activity!!.title = mUsername
        }
        mProfileView!!.visibility = View.VISIBLE
        onRefreshData()
    }

    override fun onRefreshData() {
        profile
    }

    private val profile: Unit
        private get() {
            mDisposable = ApiUtils.getApiService()!!.getUserInfo(mUsername)
                    .subscribeOn(Schedulers.io())
                    .doOnSuccess { response: UserResponse? -> response?.let { mStorage!!.insertUser(it) } }
                    .onErrorReturn { throwable: Throwable -> if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.javaClass)) mUsername?.let { mStorage!!.getUser(it) } else null }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { disposable: Disposable? -> mRefreshOwner!!.setRefreshState(true) }
                    .doFinally { mRefreshOwner!!.setRefreshState(false) }
                    .subscribe(
                            { response: UserResponse? ->
                                mErrorView!!.visibility = View.GONE
                                mProfileView!!.visibility = View.VISIBLE
                                bind(response?.user)
                            }
                    ) { throwable: Throwable? ->
                        mErrorView!!.visibility = View.VISIBLE
                        mProfileView!!.visibility = View.GONE
                    }
        }

    private fun bind(user: User?) {
        Picasso.get()
                .load(user?.image?.photoUrl)
                .fit()
                .into(mProfileImage)
        mProfileName?.text = user?.displayName
        mProfileCreatedOn!!.text = DateUtils.format(user?.createdOn ?: 0L)
        mProfileLocation?.text = user?.location
    }

    override fun onDetach() {
        mStorage = null
        mRefreshOwner = null
        if (mDisposable != null) {
            mDisposable!!.dispose()
        }
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