package com.example.energystory.data.status

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "status_table")
data class Status(
        @PrimaryKey(autoGenerate = false)
        val gameID: Int,

        val budget: Int,        // million $
        val energy: Int,        // Mtoe
        val approvalRate: Int,  // 0.0 ~ 100.0 (%)
        val pollution: Int,     // 0 ~ 100  (%)

        val generatorNumber: Int
) {
        constructor(gameID: Int) : this(gameID, StartStatus.Budget.value, StartStatus.Energy.value, StartStatus.ApprovalRate.value, StartStatus.Pollution.value, 1) {  }

        enum class StartStatus(val value: Int) {
                Budget(15000),
                Energy(0),
                ApprovalRate(100),
                Pollution(50)
        }
}