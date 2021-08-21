package com.example.energystory.data.tile

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Tile::class], version = 1, exportSchema = false)
abstract class TileDatabase: RoomDatabase() {
    abstract fun tileDao(): TileDao

    companion object {
        @Volatile
        private var INSTANCE: TileDatabase? = null

        fun getInstance(context: Context): TileDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            TileDatabase::class.java,
                            "tile_database"
                    ).allowMainThreadQueries().build()
                }
            }

            return INSTANCE!!
        }
    }
}