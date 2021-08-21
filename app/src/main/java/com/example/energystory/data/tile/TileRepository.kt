package com.example.energystory.data.tile

import android.app.Application
import androidx.lifecycle.LiveData
import java.lang.Exception
import kotlin.concurrent.thread

class TileRepository(application: Application) {
    val tileDatabase = TileDatabase.getInstance(application)
    val tileDao = tileDatabase.tileDao()

    fun findByGameIdandPosition(gameID: Int, position: Int): Tile? {
        return tileDao.findByGameIDandPosition(gameID, position)
    }

    fun insert(tile: Tile) {
        try {
            val thread = Thread(Runnable {
                tileDao.insert(tile)
            })
            thread.start()
        } catch (e: Exception) {e.printStackTrace()}
    }

    fun delete(gameID: Int) {
        try {
            val thread = Thread(Runnable {
                tileDao.deleteByGameID(gameID)
            })
            thread.start()
        } catch (e: Exception) {e.printStackTrace()}
    }
}