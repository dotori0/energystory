package com.example.energystory.data.tile

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tile_table")
data class Tile(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val gameID: Int,

        val position: Int // position number
)