package com.example.energystory.data.process

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Process::class], version = 1, exportSchema = false)
abstract class ProcessDatabase: RoomDatabase() {
    abstract fun processDao(): ProcessDao

    companion object {
        @Volatile
        private var INSTANCE: ProcessDatabase? = null

        fun getInstance(context: Context): ProcessDatabase? {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ProcessDatabase::class.java,
                            "process_database"
                    ).allowMainThreadQueries().build()
                }
            }

            return INSTANCE
        }
    }
}