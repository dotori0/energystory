package com.example.energystory.data.process

import androidx.room.*

@Dao
interface ProcessDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addProcess(process: Process)

    @Query("SELECT * FROM process_table WHERE id=:id")
    fun findProcess(id: Long): Process?

    @Update
    fun updateProcess(process: Process)
}