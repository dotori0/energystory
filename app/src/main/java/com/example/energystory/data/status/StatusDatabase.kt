package com.example.energystory.data.status

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Status::class], version = 1, exportSchema = false)
abstract class StatusDatabase: RoomDatabase() {
    abstract fun statusDao(): StatusDao

    companion object {
        @Volatile
        private var INSTANCE: StatusDatabase? = null

        fun getInstance(context: Context): StatusDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            StatusDatabase::class.java,
                            "status_database"
                    ).allowMainThreadQueries().build()
                }
            }

            return INSTANCE!!
        }
    }
}