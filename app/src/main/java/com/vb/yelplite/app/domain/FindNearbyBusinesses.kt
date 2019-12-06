package com.vb.yelplite.app.domain

import io.reactivex.Observable

class FindNearbyBusinesses() {
    fun run(): Observable<Business> {
        return Observable.just(Business("teste"))
    }
}