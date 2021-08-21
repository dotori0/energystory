package com.example.energystory.data.tile

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tile: Tile)

    @Query("SELECT * FROM tile_table WHERE gameID=:gameID and position=:position")
    fun findByGameIDandPosition(gameID: Int, position: Int): Tile?

    @Query("DELETE FROM tile_table WHERE gameID=:gameID")
    fun deleteByGameID(gameID: Int)
}