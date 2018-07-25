package com.multazamgsd.footballschedule.service

import com.multazamgsd.footballschedule.model.NextResponse
import com.multazamgsd.footballschedule.model.PreviousResponse
import com.multazamgsd.footballschedule.model.TeamDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("/api/v1/json/1/eventspastleague.php")
    fun eventsPastLeague(@Query("id") id: String): Call<PreviousResponse>

    @GET("/api/v1/json/1/eventsnextleague.php")
    fun eventsNextMatch(@Query("id") id: String): Call<NextResponse>

    @GET("/api/v1/json/1/lookupevent.php")
    fun lookupEvent(@Query("id") id: String): Call<PreviousResponse>

    @GET("/api/v1/json/1/lookupteam.php")
    fun teamDetail(@Query("id") id: String): Call<TeamDetailResponse>
}