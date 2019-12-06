package com.vb.yelplite.core

interface Mapper<T, R> {
    fun map(original: T): R
}