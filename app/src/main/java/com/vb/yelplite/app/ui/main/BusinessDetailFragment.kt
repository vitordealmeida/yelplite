package com.vb.yelplite.app.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.vb.yelplite.app.R
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class BusinessDetailFragment : Fragment() {
    companion object {
        fun newInstance(id: String?): BusinessDetailFragment {
            val fragment = BusinessDetailFragment()
            val bundle = Bundle().apply { putString("id", id) }
            fragment.arguments = bundle
            return fragment
        }
    }

    lateinit var viewModel: BusinessDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.business_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel { parametersOf(arguments?.getString("id")) }

        viewModel.businessDetails.observe(viewLifecycleOwner) {
            
        }
    }
}