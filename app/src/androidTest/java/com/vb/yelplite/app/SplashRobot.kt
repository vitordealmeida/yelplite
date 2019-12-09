package com.vb.yelplite.app

import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.vb.yelplite.app.ui.main.MainActivity
import com.vb.yelplite.app.ui.splash.SplashActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SplashRobot : BaseTestRobot() {

    fun clickAccept() = clickButton(R.id.splash_permission_btn)
    fun checkMessageTextColorMatches(color: Int) =
        checkTextColorMatches(view(R.id.splash_permission_tv), color)

    fun login(func: SplashRobot.() -> Unit) = SplashRobot().apply { func() }

    @get:Rule
    var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    @get:Rule
    val mainActivityRule = IntentsTestRule(SplashActivity::class.java)

    @Test
    fun splashOpensMainActivityWhenGrantedPermission() {
        login {
            Thread.sleep(3000)
            intended(hasComponent(MainActivity::class.java.name))
        }
    }
}