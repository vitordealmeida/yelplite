package com.vb.yelplite.app.ui.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.vb.yelplite.app.R
import com.vb.yelplite.app.ui.main.MainActivity
import kotlinx.android.synthetic.main.splash_activity.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel
    private val permissionRequestCode = 42

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        viewModel = getViewModel()
        lifecycle.addObserver(viewModel)

        viewModel.splashAction.observe(this, Observer { action ->

            when (action) {
                SplashAction.REQUEST_PERMISSION -> {
                    splash_permission_btn.visibility = View.VISIBLE
                    splash_permission_tv.visibility = View.VISIBLE
                    splash_permission_tv.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.primaryTextColor,
                            theme
                        )
                    )
                    splash_permission_btn.setOnClickListener {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ),
                            permissionRequestCode
                        )
                    }
                }
                SplashAction.FINISH -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    overridePendingTransition(0, R.anim.splash_fadeout)
                }
                SplashAction.NOTHING -> {
                    splash_permission_btn.visibility = View.GONE
                    splash_permission_tv.visibility = View.GONE
                }
                SplashAction.REINFORCE_PERMISSION -> {
                    val animShake = AnimationUtils.loadAnimation(this, R.anim.error_shake)
                    splash_permission_tv.startAnimation(animShake)
                    splash_permission_btn.startAnimation(animShake)
                    splash_permission_tv.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.design_default_color_error,
                            theme
                        )
                    )
                }
                else -> {}
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            permissionRequestCode -> {
                if ((grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED })) {
                    viewModel.onPermissionsGranted()
                } else {
                    viewModel.onPermissionsDenied()
                }
                return
            }

            else -> {
            }
        }
    }
}