package com.example.fajar.unyapps.Home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fajar.unyapps.R

/**
 * Created by fajar on 29/12/17.
 */
class HomeFragment : Fragment() {

    companion object {
        val TAG: String = HomeFragment::class.java.simpleName
        fun newInstance() = HomeFragment()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity!!.title = getString(R.string.home)
        var rootView = inflater.inflate(R.layout.fragment_home, container, false)
        return rootView

    }
}