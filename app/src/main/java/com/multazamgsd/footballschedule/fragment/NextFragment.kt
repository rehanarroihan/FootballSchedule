package com.multazamgsd.footballschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.multazamgsd.footballschedule.R
import com.multazamgsd.footballschedule.adapter.NextAdapter
import com.multazamgsd.footballschedule.model.Events
import com.multazamgsd.footballschedule.model.NextResponse
import com.multazamgsd.footballschedule.service.Client
import com.multazamgsd.footballschedule.service.RetrofitService
import kotlinx.android.synthetic.main.fragment_next.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NextFragment : Fragment() {
    private var TAG = "NextFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mRetrofit: RetrofitService? = Client.FetchService()
        mRetrofit?.eventsNextMatch("4328")?.enqueue(object : Callback<NextResponse> {
            override fun onFailure(call: Call<NextResponse>?, t: Throwable?) {
                Log.d(TAG, t.toString())
            }

            override fun onResponse(call: Call<NextResponse>?, response: Response<NextResponse>?) {
                var eventList: List<Events> = response?.body()!!.events
                recyclerViewNext.layoutManager = LinearLayoutManager(activity)
                recyclerViewNext.adapter = NextAdapter(activity, eventList) {

                }
            }
        })
    }
}
