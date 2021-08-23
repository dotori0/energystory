package com.example.energystory.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.energystory.data.country.Country
import com.example.energystory.data.process.Process
import com.example.energystory.data.process.ProcessDatabase
import com.example.energystory.data.status.Status
import com.example.energystory.data.status.StatusRepository
import com.example.energystory.data.tile.Tile
import com.example.energystory.data.tile.TileRepository

class GameViewModel(application: Application): AndroidViewModel(application) {
    // data
    val countries: Array<Country> = arrayOf(
            Country("Moldova", 72, 43, 43, 38),
            Country("Russia", 99, 94, 49, 12),
            Country("Columbia", 73, 90, 31, 48),
            Country("Korea", 70, 39, 21, 10),
            Country("RSA", 67, 83, 37, 37),
            Country("Italy", 35, 86, 45, 44),
            Country("Singapore", 33, 44, 61, 86),
            Country("Germany", 89, 93, 24, 25),
            Country("Denmark", 66, 29, 40, 39),
            Country("Cambodia", 86, 80, 31, 10),
            Country("Morocco", 62, 90, 29, 31),
            Country("France", 96, 33, 26, 9),
            Country("Spain", 73, 86, 43, 45),
            Country("Brazil", 86, 59, 88, 69),
            Country("Canada", 86, 76, 76, 59),
            Country("Egypt", 57, 30, 43, 29),
            Country("USA", 75, 28, 29, 13),
            Country("Argentina", 60, 30, 33, 28),
            Country("Tanzania", 38, 61, 10, 17),
            Country("Chile", 53, 41, 31, 34),
            Country("Rwanda", 33, 39, 56, 96),
            Country("England", 25, 65, 47, 54),
            Country("butane", 45, 29, 53, 30),
            Country("Japan", 55, 59, 30, 25),
            Country("Philippine", 69, 26, 34, 16),
            Country("Swiss", 20, 55, 5, 49),
            Country("Malaysia", 54, 61, 21, 41),
            Country("Bangladesh", 62, 55, 48, 36),
            Country("Uzbekistan", 95, 48, 78, 87),
            Country("India", 30, 58, 56, 20)
    )

    // Database Repository
    private val statusRepository = StatusRepository(application)
    private val tileRepository = TileRepository(application)

    // User Select
    private var energyGeneratorType: Int = 0 // Energy Generator Numbers
    var selected_country = 0
    var wind: Int = 100
    var sunPower: Int = 100
    var sunLight: Int = 100
    var precipitation: Int = 0 // Korean Translate: 강수량

    // Live Data
    val changedStatus: MutableLiveData<Status> = MutableLiveData<Status>(Status(0))
    val gameResult: MutableLiveData<Int> = MutableLiveData(0) // 0: Not End, 1: Game Over, 2: Game Win


    fun getStatus(): LiveData<Status> {
        return statusRepository.find(1)
    }

    fun getStatusSync(): Status {
        return statusRepository.findSync(1)
    }

    fun changeStatus(status: Status) {
        statusRepository.update(status)
    }

    fun getTile(position: Int): Tile? {
        return tileRepository.findByGameIdandPosition(1, position)
    }

    fun insertTile(tile: Tile) {
        tileRepository.insert(tile)
    }

    fun changeEnergyGeneratorType(number: Int) {
        energyGeneratorType = number
    }

    fun result() {
        val oldStatus = getStatusSync()

        // Change Budget
        var budgetChange = when(energyGeneratorType) {
            1 -> -11000
            2 -> -15000
            3 -> -30000
            else -> 0
        }

        // Change Energy
        var energyChange = when(energyGeneratorType) {
            1 -> if (sunLight < 50 || precipitation > 50) 70 else 210
            2 -> if (sunPower < 50 || precipitation > 50) 150 else 450
            3 -> if (wind < 50) 300 else 900
            else -> 0
        }

        // Change Pollution
        var pollutionChange = 0

        // Change Approval Rate
        var approvalRateChange = when(energyGeneratorType) {
            1 -> if (sunLight < 50 || precipitation > 50) -10 else 4
            2 -> if (sunPower < 50 || precipitation > 50) -10 else 4
            3 -> if (wind < 50) -10 else 4
            else -> 0
        }
        budgetChange += (oldStatus.approvalRate + approvalRateChange) / 10 * 1000

        // Apply Change
        changedStatus.value = Status(0, budgetChange, energyChange, approvalRateChange, pollutionChange, 0)

        val newStatus = Status(oldStatus.gameID,
                oldStatus.budget + budgetChange, oldStatus.energy + energyChange,
                oldStatus.approvalRate + approvalRateChange, oldStatus.pollution + pollutionChange,
                oldStatus.generatorNumber)
        statusRepository.update(newStatus)

        // game over or win
        // lose
        if(newStatus.budget <= 0) {
            gameResult.value = 1
            restartGame1()
        }
        // win
        else if(newStatus.energy >= 10000) {
            gameResult.value = 2
            restartGame1()
        }
    }

    fun restartGame1() {
        val processDatabase = ProcessDatabase.getInstance(this.getApplication())!!
        processDatabase.processDao().updateProcess(Process(1, false, false))
    }
}


/*
* Energy Generator Numbers
* 1. Photovoltaics Generator
* 2. Solar Thermal Generator
* 3. Wind Power Generator
*/