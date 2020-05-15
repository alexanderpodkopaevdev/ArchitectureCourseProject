package com.alexanderPodkopaev.dev.behancer.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.alexanderPodkopaev.dev.behancer.AppDelegate
import com.alexanderPodkopaev.dev.behancer.R
import com.alexanderPodkopaev.dev.behancer.data.Storage
import com.alexanderPodkopaev.dev.behancer.data.Storage.StorageOwner


abstract class SingleFragmentActivity : AppCompatActivity(), StorageOwner, OnRefreshListener, RefreshOwner {
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_swipe_container)
        mSwipeRefreshLayout = findViewById(R.id.refresher)
        mSwipeRefreshLayout?.setOnRefreshListener(this)
        if (savedInstanceState == null) {
            changeFragment(fragment)
        }
    }

    protected abstract val fragment: Fragment
    override fun obtainStorage(): Storage? {
        return (applicationContext as AppDelegate).storage
    }

    fun changeFragment(fragment: Fragment) {
        val addToBackStack = supportFragmentManager.findFragmentById(R.id.fragmentContainer) != null
        val transaction = supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }
        transaction.commit()
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
        mSwipeRefreshLayout!!.post { mSwipeRefreshLayout!!.isRefreshing = refreshing }
    }
}