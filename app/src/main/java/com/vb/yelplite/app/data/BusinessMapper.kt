package com.vb.yelplite.app.data

import com.vb.yelplite.app.domain.Business
import com.vb.yelplite.app.domain.Mapper

class BusinessMapper : Mapper<RemoteBusiness, Business> {
    override fun map(original: RemoteBusiness): Business {
        return Business(original.name)
    }
}