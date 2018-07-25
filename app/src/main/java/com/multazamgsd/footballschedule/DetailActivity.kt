package com.multazamgsd.footballschedule

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.multazamgsd.footballschedule.R.id.action_fav
import com.multazamgsd.footballschedule.R.id.action_unfav
import com.multazamgsd.footballschedule.model.Events
import com.multazamgsd.footballschedule.model.Favorite
import com.multazamgsd.footballschedule.model.TeamDetailResponse
import com.multazamgsd.footballschedule.model.Teams
import com.multazamgsd.footballschedule.service.Client
import com.multazamgsd.footballschedule.service.RetrofitService
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import retrofit2.Call
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private var TAG: String = "DetailActivity"
    private lateinit var events: Events
    private lateinit var favorite: Favorite
    private var homeBadge: String = ""
    private var awayBadge: String = ""
    private var homeGoal: String = ""
    private var awayGoal: String = ""
    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Match Detail"

        var detailParam: String = ""
        if (detailParam === "previous") {
            events = intent?.extras?.get("event") as Events
            initPrevious()
        } else if (detailParam === "favorite") {
            favorite = intent?.extras?.get("favorite") as Favorite
            initFavorite()
        }
    }

    private fun initPrevious() {
        tvClub.text = events.strEvent
        tvScore.text = "${events.intHomeScore} : ${events.intAwayScore}"
        tvDate.text = events.strDate
        events.strHomeGoalDetails.split(";").toTypedArray().forEach {
            homeGoal += it + "\n"
        }
        events.strAwayGoalDetails.split(";").toTypedArray().forEach {
            awayGoal += it + "\n"
        }
        tvHomeGoal.text = homeGoal
        tvAwayGoal.text = awayGoal

        val teamID = arrayOf(events.idHomeTeam, events.idAwayTeam)
        val stringTeamBadge = arrayOf(homeBadge, awayBadge)
        val teamBadge = arrayOf(ivHomeTeam, ivAwayTeam)
        for (i in 0 until teamID.size) {
            val mRetrofit: RetrofitService? = Client.FetchService()
            mRetrofit?.teamDetail(teamID[i])?.enqueue(object : retrofit2.Callback<TeamDetailResponse> {
                override fun onFailure(call: Call<TeamDetailResponse>?, t: Throwable?) {
                    Log.d(TAG, t.toString())
                    val snackbar: Snackbar = Snackbar.make(llDetail, "Cant retrieve data from server", Snackbar.LENGTH_LONG)
                    snackbar.show()
                }

                override fun onResponse(call: Call<TeamDetailResponse>?, detailResponse: Response<TeamDetailResponse>?) {
                    val teams: Teams = detailResponse!!.body()!!.teams[0]
                    Glide.with(this@DetailActivity).load(teams.strTeamBadge).into(teamBadge[i])
                    stringTeamBadge[i] = teams.strTeamBadge
                }

            })
        }
    }

    private fun initFavorite() {

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "onPrepareOptionsMenu")
        if (isFavorite()) {
            menu?.findItem(R.id.action_fav)?.isVisible = false
            menu?.findItem(R.id.action_unfav)?.isVisible = true
            Log.d(TAG, "onPrepareOptionsMenu: isFavorite")
        } else {
            menu?.findItem(R.id.action_fav)?.isVisible = true
            menu?.findItem(R.id.action_unfav)?.isVisible = false
            Log.d(TAG, "onPrepareOptionsMenu: not fav")
        }
        return super.onPrepareOptionsMenu(menu)
    }

    private fun isFavorite(): Boolean {
        var count: Int = 0
        database.use {
            select("TABLE_FAVORITE").whereArgs("(EVENT_ID = ${events.idEvent})")
                    .exec {
                        count = this.count
                    }
        }
        Log.d(TAG, "Fav count: " + count.toString())
        return count > 0
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.EVENT_ID to events.idEvent,
                        Favorite.EVENT_TITLE to events.strEvent,
                        Favorite.EVENT_SCORE to "${events.intHomeScore} : ${events.intAwayScore}",
                        Favorite.EVENT_DTE to events.strDate,
                        Favorite.TEAM_HOME_BADGE to homeBadge,
                        Favorite.TEAM_AWAY_BADGE to awayBadge,
                        Favorite.TEAM_HOME_NAME to events.strHomeTeam,
                        Favorite.TEAM_AWAY_NAME to events.strAwayTeam,
                        Favorite.HOME_GOAL to events.strHomeGoalDetails,
                        Favorite.AWAY_GOAL to events.strAwayGoalDetails)
            }
            snackbar = Snackbar.make(llDetail, "Added to Favorite", Snackbar.LENGTH_LONG)
            snackbar.show()
            invalidateOptionsMenu()
        } catch (e: SQLiteConstraintException) {
            snackbar = Snackbar.make(llDetail, e.localizedMessage, Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }

    private fun deleteFromFavorite() {
        database.use {
            try {
                delete("TABLE_FAVORITE", "EVENT_ID = ${events.idEvent}")
                snackbar = Snackbar.make(llDetail, "Deleted from Favorite", Snackbar.LENGTH_LONG)
                snackbar.show()
                invalidateOptionsMenu()
            } catch (e: SQLiteConstraintException) {
                snackbar = Snackbar.make(llDetail, e.localizedMessage, Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            action_fav -> {
                addToFavorite()
                true
            }
            action_unfav -> {
                deleteFromFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}
