package com.vb.yelplite.app.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.vb.yelplite.app.R
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.dsl.module

val fragmentModule = module {
    factory { MainFragment() }
}

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var viewModel: BusinessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel()

        viewModel.businesses.observe(viewLifecycleOwner, Observer {businesses ->
            Log.d("VB", "Found ${businesses.size} businesses")
            businesses.forEach { Log.d("VB", "> " + it.name) }
        })
    }

}
