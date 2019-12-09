package com.vb.yelplite.app

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasTextColor
import androidx.test.espresso.matcher.ViewMatchers.withId

open class BaseTestRobot {

    fun view(resId: Int): ViewInteraction = onView((withId(resId)))
    fun clickButton(resId: Int): ViewInteraction = view(resId).perform(ViewActions.click())

    fun checkTextColorMatches(viewInteraction: ViewInteraction, colorId: Int): ViewInteraction =
        viewInteraction
            .check(matches(hasTextColor(colorId)))

}


