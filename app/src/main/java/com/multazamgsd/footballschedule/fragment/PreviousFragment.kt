package com.multazamgsd.footballschedule.fragment

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.multazamgsd.footballschedule.DetailActivity

import com.multazamgsd.footballschedule.R
import com.multazamgsd.footballschedule.adapter.PreviousAdapter
import com.multazamgsd.footballschedule.model.Events
import com.multazamgsd.footballschedule.model.PreviousResponse
import com.multazamgsd.footballschedule.service.Client
import com.multazamgsd.footballschedule.service.RetrofitService
import kotlinx.android.synthetic.main.fragment_previous.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreviousFragment : Fragment() {
    var TAG: String = "PreviousFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_previous, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swipePrevious.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)

        swipePrevious.setOnRefreshListener {
            getPreviousMatchData()
        }
        getPreviousMatchData()
    }

    private fun getPreviousMatchData() {
        swipePrevious.isRefreshing = true
        val mRetrofit: RetrofitService? = Client.FetchService()
        mRetrofit?.eventsPastLeague("4335")?.enqueue(object : Callback<PreviousResponse> {
            override fun onFailure(call: Call<PreviousResponse>?, t: Throwable?) {
                Log.d(TAG, t.toString())
                swipePrevious.isRefreshing = false
            }

            override fun onResponse(call: Call<PreviousResponse>?, response: Response<PreviousResponse>?) {
                var eventList: List<Events> = response?.body()!!.events
                if (eventList.isNotEmpty()) {
                    recyclerViewPrevious.layoutManager = LinearLayoutManager(activity)
                    recyclerViewPrevious.adapter = PreviousAdapter(activity, eventList, {
                        val i = Intent(context, DetailActivity::class.java)
                        i.putExtra("event_id", it.idEvent)
                        startActivity(i)
                    })
                    Log.d(TAG, eventList.toString())
                } else {
                    val snackbar: Snackbar
                    snackbar = Snackbar.make(swipePrevious, "Tidak ada data", Snackbar.LENGTH_LONG)
                    snackbar.show()
                }
                swipePrevious.isRefreshing = false
            }
        })
    }

}
