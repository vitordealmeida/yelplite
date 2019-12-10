package com.vb.yelplite.app.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.lifecycle.observe
import com.vb.yelplite.app.R
import kotlinx.android.synthetic.main.activity_business_detail.*
import kotlinx.android.synthetic.main.business_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BusinessDetailActivity : AppCompatActivity() {

    val viewModel: BusinessDetailsViewModel by viewModel { parametersOf(intent.getStringExtra("id")) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_detail)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.business_detail_container,
                    BusinessDetailFragment.newInstance(intent.getStringExtra("id"))
                )
                .commitNow()
        }

        viewModel.businessDetails.observe(this) {
            business_detail_loading_progressbar.hide()
        }

        viewModel.businessReviews.observe(this) {
            business_detail_loading_progressbar.hide()
        }
    }
}
