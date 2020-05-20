package com.alexanderPodkopaev.dev.behancer.ui.userProjects

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
import com.alexanderPodkopaev.dev.behancer.ui.projects.ProjectsAdapter
import com.alexanderPodkopaev.dev.behancer.ui.projects.ProjectsPresenter
import com.alexanderPodkopaev.dev.behancer.ui.projects.ProjectsView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class UserProjectsFragment : PresenterFragment(), ProjectsView, Refreshable, ProjectsAdapter.OnItemClickListener {

    lateinit var mRecyclerView: RecyclerView
    private var mRefreshOwner: RefreshOwner? = null
    lateinit var mErrorView: View
    private var mStorage: Storage? = null
    private var mProjectsAdapter: ProjectsAdapter? = null

    private var mUsername: String? = null


    @InjectPresenter
    internal lateinit var mPresenter: ProjectsPresenter

    @ProvidePresenter
    fun providePresenter() : ProjectsPresenter {
        return  ProjectsPresenter( mStorage)

    }

    override fun getPresenter(): ProjectsPresenter? {
        return mPresenter
    }

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
        activity?.setTitle(R.string.projects)
       //mProjectsAdapter = ProjectsAdapter(projects, this)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mProjectsAdapter
        mUsername = arguments?.getString(PROJECT_KEY)
        onRefreshData()
    }

    override fun onItemClick(username: String?) {
        //mPresenter.openProfileFragment(username)
    }

    override fun onDetach() {
        mStorage = null
        mRefreshOwner = null
        super.onDetach()
    }

    override fun onRefreshData() {
        mPresenter.getProjects(mUsername)
    }


    override fun showProjects(projects: List<Project>?) {
        mErrorView.visibility = View.GONE
        mRecyclerView.visibility = View.VISIBLE
        //mProjectsAdapter?.addData(projects, true)
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
        mErrorView.visibility = View.VISIBLE
        mRecyclerView.visibility = View.GONE
    }


    companion object {
        const val PROJECT_KEY = "PROJECT_KEY"
        fun newInstance(args: Bundle?): UserProjectsFragment {
            val fragment = UserProjectsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}