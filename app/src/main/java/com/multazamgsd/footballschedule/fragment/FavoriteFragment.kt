package com.multazamgsd.footballschedule.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.multazamgsd.footballschedule.DetailActivity
import com.multazamgsd.footballschedule.R
import com.multazamgsd.footballschedule.R.color.colorAccent
import com.multazamgsd.footballschedule.adapter.FavoriteAdapter
import com.multazamgsd.footballschedule.database
import com.multazamgsd.footballschedule.model.Favorite
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteFragment : Fragment() {
    private var TAG = "FavoriteFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipeFavorite.setColorSchemeResources(colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)

        swipeFavorite.setOnRefreshListener {
            showFavoriteList()
        }
        showFavoriteList()
    }

    private fun showFavoriteList() {
        swipeFavorite.isRefreshing = true

        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE)
            val mList = result.parseList(classParser<Favorite>())
            if (mList.isNotEmpty()) {
                tvFavoriteNull.visibility = View.GONE
                recyclerViewFav.layoutManager = LinearLayoutManager(activity)
                recyclerViewFav.visibility = View.VISIBLE
                Log.d(TAG, mList.toString())
                recyclerViewFav.adapter = FavoriteAdapter(activity, mList) {
                    val i = Intent(context, DetailActivity::class.java)
                    i.putExtra("event_id", it.event_id)
                    startActivity(i)
                }
                swipeFavorite.isRefreshing = false
            } else {
                recyclerViewFav.visibility = View.GONE
                tvFavoriteNull.visibility = View.VISIBLE
                Log.d(TAG, "mList is empty")
                swipeFavorite.isRefreshing = false
            }
        }
    }
}
