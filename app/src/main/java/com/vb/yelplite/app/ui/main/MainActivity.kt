package com.vb.yelplite.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import com.vb.yelplite.app.R
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val isTablet = resources.getBoolean(R.bool.isTablet)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_business_list_container, BusinessListFragment.newInstance())
                .commitNow()
        }

        lifecycle.addObserver(viewModel)

        viewModel.state.observe(this) {
            if (it != MainAction.NOTHING) {
                Snackbar.make(
                    findViewById<View>(android.R.id.content),
                    if (it == MainAction.ERROR_INTERNET) R.string.error_internet else R.string.error_gps,
                    Snackbar.LENGTH_LONG
                )
                    .setAction(R.string.error_action_retry) {
                        viewModel.fetchBusinesses()
                    }
                    .setDuration(Snackbar.LENGTH_INDEFINITE)
                    .show()
            }
        }

        viewModel.businesses.observe(this) {
            main_content_loading_progressbar.hide()
            if (isTablet)
                viewModel.selectedBusinessId.value = it[0].id
        }

        if (!isTablet) {
            viewModel.selectedBusinessId.observe(this) {
                if (it != null) {
                    startActivity(
                        Intent(this, BusinessDetailActivity::class.java)
                            .putExtra("id", it)
                    )
                }
            }
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.business_detail_container, BusinessDetailFragment.newInstance(null))
                .commitNow()
        }
    }

}
