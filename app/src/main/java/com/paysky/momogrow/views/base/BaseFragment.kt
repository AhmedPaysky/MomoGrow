package com.paysky.momogrow.views.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.paysky.momogrow.R
import java.lang.Exception

open class BaseFragment : Fragment() {

    fun addNewFragment(fragment: Fragment?, TAG: String?) {

        val fragmentManager = (context as AppCompatActivity?)!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.addToBackStack(TAG)
        if (fragment != null) {
            fragmentTransaction.replace(R.id.fragments_container, fragment)
                .commitAllowingStateLoss()
        }

    }

    fun addNewFragmentNullBackStack(fragment: Fragment?, TAG: String?) {

        val fragmentManager = (context as AppCompatActivity?)!!.supportFragmentManager
            ?: return
        val fragmentTransaction = fragmentManager.beginTransaction()

        try {
            if (fragment != null)
                fragmentTransaction.replace(R.id.fragments_container, fragment, TAG).commit()

        } catch (e: Exception) {
        }
    }

}