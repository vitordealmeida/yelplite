package com.vb.yelplite.app.ui.splash

import android.Manifest
import androidx.lifecycle.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class SplashAction {
    NOTHING,
    REQUEST_PERMISSION,
    REINFORCE_PERMISSION,
    FINISH
}

class SplashViewModel(val permissionChecker: (String) -> Boolean) : ViewModel(), LifecycleObserver {

    val splashAction = MutableLiveData<SplashAction>(SplashAction.NOTHING)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        verifyPermissions()
    }

    fun verifyPermissions() {
        viewModelScope.launch {
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
        return permissionChecker(permission)
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