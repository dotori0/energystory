package com.example.energystory.data.status

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StatusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(status: Status)

    @Query("SELECT * FROM status_table WHERE gameID=:gameID")
    fun find(gameID: Int): LiveData<Status>

    @Query("SELECT * FROM status_table WHERE gameID=:gameID")
    fun findSync(gameID: Int): Status

    @Update
    fun update(status: Status)
}