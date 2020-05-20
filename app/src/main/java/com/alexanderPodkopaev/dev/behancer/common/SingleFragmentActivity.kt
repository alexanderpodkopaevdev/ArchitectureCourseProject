package com.alexanderPodkopaev.dev.behancer.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alexanderPodkopaev.dev.behancer.R


abstract class SingleFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        if (savedInstanceState == null) {
            changeFragment(fragment)
        }
    }

    open fun getLayout() : Int {
            return R.layout.ac_container
    }

    protected abstract val fragment: Fragment


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


}