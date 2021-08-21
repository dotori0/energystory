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
            Country("1",72,43,38,43),
            Country("2",99,94,12,49),
            Country("3",73,90,48,31),
            Country("4",70,39,10,21),
            Country("5",67,83,37,37),
            Country("6",35,86,74,45),
            Country("7",33,44,86,61),
            Country("8",89,93,25,24),
            Country("9",66,29,39,40),
            Country("10",86,80,10,31),
            Country("11",62,90,31,29),
            Country("12",96,33,9,26),
            Country("13",73,86,45,43),
            Country("14",86,59,69,88),
            Country("15",86,76,59,76),
            Country("16",57,30,29,43),
            Country("17",75,28,13,29),
            Country("18",60,30,28,33),
            Country("19",38,61,17,10),
            Country("20",53,41,34,31),
            Country("21",33,39,96,56),
            Country("22",25,65,54,47),
            Country("23",45,29,30,53),
            Country("24",55,59,25,30),
            Country("25",69,26,16,34),
            Country("26",20,55,49,5),
            Country("27",54,61,41,21),
            Country("28",62,55,36,48),
            Country("29",95,48,87,78),
            Country("30",30,30,20,36)
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
            3 -> if (wind < 50) 200 else 600
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