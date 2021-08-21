package com.example.energystory.data.process

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "process_table")
data class Process (
        @PrimaryKey(autoGenerate = false)
        val id: Long,

        @ColumnInfo(defaultValue = "false")
        val storyDone: Boolean,

        @ColumnInfo(defaultValue = "false")
        val tutorialDone: Boolean
)