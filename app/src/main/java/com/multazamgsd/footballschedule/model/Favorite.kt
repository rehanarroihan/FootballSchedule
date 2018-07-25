package com.multazamgsd.footballschedule.model

import java.io.Serializable

data class Favorite(val id: Long?, val event_id: String?, val event_title: String?, val event_dte: String?,
                    val team_home_badge: String?, val team_away_badge: String?, val event_score: String?,
                    val team_home_name: String?, val team_away_name: String?, val home_goal: String?,
                    val away_goal: String?) : Serializable {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_TITLE: String = "EVENT_TITLE"
        const val EVENT_DTE: String = "EVENT_DTE"
        const val TEAM_HOME_BADGE: String = "TEAM_HOME_BADGE"
        const val TEAM_AWAY_BADGE: String = "TEAM_AWAY_BADGE"
        const val EVENT_SCORE: String = "EVENT_SCORE"
        const val TEAM_HOME_NAME: String = "TEAM_HOME_NAME"
        const val TEAM_AWAY_NAME: String = "TEAM_AWAY_NAME"
        const val HOME_GOAL: String = "HOME_GOAL"
        const val AWAY_GOAL: String = "AWAY_GOAL"
    }
}