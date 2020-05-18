package com.alexanderPodkopaev.dev.behancer.ui.projects

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexanderPodkopaev.dev.behancer.R
import com.alexanderPodkopaev.dev.behancer.common.PresenterFragment
import com.alexanderPodkopaev.dev.behancer.common.RefreshOwner
import com.alexanderPodkopaev.dev.behancer.common.Refreshable
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.Storage.StorageOwner
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import com.alexanderPodkopaev.dev.behancer.ui.profile.ProfileActivity
import com.alexanderPodkopaev.dev.behancer.ui.profile.ProfileFragment


class ProjectsFragment : PresenterFragment<ProjectsPresenter>(), ProjectsView, Refreshable, ProjectsAdapter.OnItemClickListener {
    private val myTag: String = "MyLOG"
    private var mRecyclerView: RecyclerView? = null
    private var mRefreshOwner: RefreshOwner? = null
    private var mErrorView: View? = null
    private var mStorage: Storage? = null
    private var mProjectsAdapter: ProjectsAdapter? = null
    lateinit var mPresenter: ProjectsPresenter

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
        mPresenter = ProjectsPresenter(this, mStorage)
        mProjectsAdapter = ProjectsAdapter(this)
        mRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        mRecyclerView!!.adapter = mProjectsAdapter
        onRefreshData()
    }

    override fun onItemClick(username: String?) {
        mPresenter.openProfileFragment(username)
    }

    override fun onDetach() {
        mStorage = null
        mRefreshOwner = null
        super.onDetach()
    }

    override fun onRefreshData() {
        mPresenter.getProjects()
    }


    override fun getPresenter(): ProjectsPresenter? {
        return mPresenter
    }

    override fun showProjects(projects: List<Project>?) {
        mErrorView?.visibility = View.GONE
        mRecyclerView?.visibility = View.VISIBLE
        mProjectsAdapter?.addData(projects, true)
    }

    override fun openProfileFragment(username: String?) {
        val intent = Intent(activity, ProfileActivity::class.java)
        val args = Bundle()
        args.putString(ProfileFragment.PROFILE_KEY, username)
        intent.putExtra(ProfileActivity.USERNAME_KEY, args)
        startActivity(intent)
    }

    override fun showRefresh() {
        mRefreshOwner?.setRefreshState(true)
    }

    override fun hideRefresh() {
        mRefreshOwner?.setRefreshState(false)
    }

    override fun showError() {
        mErrorView?.visibility = View.VISIBLE
        mRecyclerView?.visibility = View.GONE
    }


    companion object {
        fun newInstance(): ProjectsFragment {
            return ProjectsFragment()
        }
    }
}