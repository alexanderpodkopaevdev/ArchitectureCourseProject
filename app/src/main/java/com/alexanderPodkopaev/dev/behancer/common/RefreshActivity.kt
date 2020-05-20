package com.alexanderPodkopaev.dev.behancer.common

import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexanderPodkopaev.dev.behancer.R

abstract class RefreshActivity : SingleFragmentActivity(), SwipeRefreshLayout.OnRefreshListener, RefreshOwner {

    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSwipeRefreshLayout = findViewById(R.id.refresher)
        mSwipeRefreshLayout.setOnRefreshListener(this)
    }

    override fun getLayout() : Int {
        return R.layout.ac_swipe_container
    }


    override fun onRefresh() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment is Refreshable) {
            (fragment as Refreshable).onRefreshData()
        } else {
            setRefreshState(false)
        }
    }

    override fun setRefreshState(refreshing: Boolean) {
        mSwipeRefreshLayout.post { mSwipeRefreshLayout.isRefreshing = refreshing }
    }


}