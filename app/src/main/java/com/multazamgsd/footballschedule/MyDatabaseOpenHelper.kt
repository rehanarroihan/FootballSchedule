package com.multazamgsd.footballschedule

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.multazamgsd.footballschedule.model.Favorite
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteMatch.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(Favorite.TABLE_FAVORITE, true,
                Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Favorite.EVENT_ID to TEXT,
                Favorite.EVENT_TITLE to TEXT,
                Favorite.EVENT_DTE to TEXT,
                Favorite.TEAM_HOME_BADGE to TEXT,
                Favorite.TEAM_AWAY_BADGE to TEXT,
                Favorite.EVENT_SCORE to TEXT,
                Favorite.TEAM_HOME_NAME to TEXT,
                Favorite.TEAM_AWAY_NAME to TEXT,
                Favorite.HOME_GOAL to TEXT,
                Favorite.AWAY_GOAL to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(Favorite.TABLE_FAVORITE, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)