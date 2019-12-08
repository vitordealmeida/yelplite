package com.vb.yelplite.app.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vb.yelplite.app.R

class BusinessDetailActivity : AppCompatActivity() {

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
    }
}
