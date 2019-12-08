package com.vb.yelplite.app.ui.splash

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.vb.yelplite.app.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class SplashAction {
    NOTHING,
    REQUEST_PERMISSION,
    REINFORCE_PERMISSION,
    FINISH
}

class SplashViewModel(val context: Context) : ViewModel(), LifecycleObserver {

    val initialMessage = MutableLiveData<Int>()

    val splashAction = MutableLiveData<SplashAction>(SplashAction.NOTHING)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        verifyPermissions()
    }

    fun verifyPermissions() {
        GlobalScope.launch {
            delay(2000)
            if (!hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) || !hasPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                splashAction.postValue(SplashAction.REQUEST_PERMISSION)
            } else {
                splashAction.postValue(SplashAction.FINISH)
            }
        }
    }

    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun onPermissionsGranted() {
        splashAction.value = SplashAction.FINISH
    }

    fun onPermissionsDenied() {
        GlobalScope.launch {
            delay(200)
            splashAction.postValue(SplashAction.REINFORCE_PERMISSION)
        }
    }
}