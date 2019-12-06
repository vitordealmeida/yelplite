package com.vb.yelplite.app.domain

interface Mapper<S, T> {
    fun map(original: S) : T
}