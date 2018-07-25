package com.multazamgsd.footballschedule.service

import com.multazamgsd.footballschedule.BuildConfig

object Client {
    var RetrofitService: RetrofitService? = null
    fun FetchService(): RetrofitService? {
        if (RetrofitService == null) {
            RetrofitService = RetrofitClient.getClient(BuildConfig.BASE_URL)?.create(com.multazamgsd.footballschedule.service.RetrofitService::class.java)
        }
        return RetrofitService
    }
}