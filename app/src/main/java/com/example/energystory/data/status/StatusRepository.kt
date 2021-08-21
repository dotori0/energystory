package com.example.energystory.data.status

import android.app.Application
import androidx.lifecycle.LiveData
import java.lang.Exception

class StatusRepository(application: Application) {
    private val statusDatabase = StatusDatabase.getInstance(application)
    private val statusDao = statusDatabase.statusDao()

    fun find(gameID: Int): LiveData<Status> {
        return statusDao.find(gameID)
    }

    fun findSync(gameID: Int): Status {
        return statusDao.findSync(gameID)
    }

    fun insert(status: Status) {
        try {
            val thread = Thread(Runnable {
                statusDao.insert(status)
            })
            thread.start()
        } catch (e: Exception) {e.printStackTrace()}
    }

    fun update(status: Status) {
        try {
            val thread = Thread(Runnable {
                statusDao.update(status)
            })
            thread.start()
        } catch (e: Exception) {e.printStackTrace()}
    }
}