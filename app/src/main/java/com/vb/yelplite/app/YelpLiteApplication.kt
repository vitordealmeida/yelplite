package com.vb.yelplite.app

import android.app.Application

open class YelpLiteApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }

    private fun initialiseDagger() {
    }
}