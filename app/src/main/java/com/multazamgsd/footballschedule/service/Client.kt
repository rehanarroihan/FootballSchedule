package com.multazamgsd.footballschedule.service

import com.multazamgsd.footballschedule.BuildConfig

object Client {
    var retrofitService: RetrofitService? = null
    fun fetchService(): RetrofitService? {
        if (retrofitService == null) {
            retrofitService = RetrofitClient.getClient(BuildConfig.BASE_URL)?.create(com.multazamgsd.footballschedule.service.RetrofitService::class.java)
        }
        return retrofitService
    }
}