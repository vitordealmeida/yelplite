package com.vb.yelplite.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.vb.yelplite.app.R
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val isTablet = resources.getBoolean(R.bool.isTablet)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BusinessListFragment.newInstance())
                .commitNow()
        }

        if (!isTablet) {
            viewModel.selectedBusinessId.observe(this, Observer {
                if (it != null) {
                    startActivity(
                        Intent(this, BusinessDetailActivity::class.java)
                            .putExtra("id", it)
                    )
                }
            })
        }
    }

}
