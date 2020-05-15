package com.alexanderPodkopaev.dev.behancer.ui.projects

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexanderPodkopaev.dev.behancer.BuildConfig
import com.alexanderPodkopaev.dev.behancer.R
import com.alexanderPodkopaev.dev.behancer.common.RefreshOwner
import com.alexanderPodkopaev.dev.behancer.common.Refreshable
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.Storage.StorageOwner
import com.alexanderPodkopaev.dev.behancer.data.model.project.ProjectResponse
import com.alexanderPodkopaev.dev.behancer.ui.profile.ProfileActivity
import com.alexanderPodkopaev.dev.behancer.ui.profile.ProfileFragment
import com.alexanderPodkopaev.dev.behancer.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class ProjectsFragment : Fragment(), Refreshable, ProjectsAdapter.OnItemClickListener {
    private val myTag: String = "MyLOG"
    private var mRecyclerView: RecyclerView? = null
    private var mRefreshOwner: RefreshOwner? = null
    private var mErrorView: View? = null
    private var mStorage: Storage? = null
    private var mProjectsAdapter: ProjectsAdapter? = null
    private var mDisposable: Disposable? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is StorageOwner) {
            mStorage = (context as StorageOwner).obtainStorage()
        }
        if (context is RefreshOwner) {
            mRefreshOwner = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_projects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mRecyclerView = view.findViewById(R.id.recycler)
        mErrorView = view.findViewById(R.id.errorView)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            activity!!.setTitle(R.string.projects)
        }
        mProjectsAdapter = ProjectsAdapter(this)
        mRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        mRecyclerView!!.adapter = mProjectsAdapter
        onRefreshData()
    }

    override fun onItemClick(username: String?) {
        val intent = Intent(activity, ProfileActivity::class.java)
        val args = Bundle()
        args.putString(ProfileFragment.Companion.PROFILE_KEY, username)
        intent.putExtra(ProfileActivity.Companion.USERNAME_KEY, args)
        startActivity(intent)
    }

    override fun onDetach() {
        mStorage = null
        mRefreshOwner = null
        if (mDisposable != null) {
            mDisposable!!.dispose()
        }
        super.onDetach()
    }

    override fun onRefreshData() {
        getProjects()
    }

    fun getProjects() {
        mDisposable = ApiUtils.getApiService()
                .getProjects(BuildConfig.API_QUERY)
                .doOnSuccess { response: ProjectResponse? -> response?.let { mStorage!!.insertProjects(it) } }
                .onErrorReturn { throwable: Throwable ->
                    Log.d(myTag, "1 ${throwable.message}")
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.javaClass)) mStorage!!.getProjects() else null
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { disposable: Disposable? -> mRefreshOwner!!.setRefreshState(true) }
                .doFinally { mRefreshOwner!!.setRefreshState(false) }
                .subscribe(
                        { response ->
                            mErrorView!!.visibility = View.GONE
                            mRecyclerView!!.visibility = View.VISIBLE
                            mProjectsAdapter!!.addData(response?.projects, true)
                        }
                ) { throwable: Throwable? ->
                    if (throwable != null) {
                        Log.d(myTag, "2 ${throwable.message}")
                    }
                    mErrorView!!.visibility = View.VISIBLE
                    mRecyclerView!!.visibility = View.GONE
                }
    }

    companion object {
        fun newInstance(): ProjectsFragment {
            return ProjectsFragment()
        }
    }
}