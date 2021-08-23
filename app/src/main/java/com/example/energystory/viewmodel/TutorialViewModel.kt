package com.example.energystory.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.energystory.data.status.Status
import com.example.energystory.data.status.StatusRepository
import com.example.energystory.data.tile.TileRepository

class TutorialViewModel(application: Application): AndroidViewModel(application) {
    val tutorialTextArray: Array<String> = arrayOf(
            "Nice to meet you sir. I am your secretary, Yoon. \n\n" +
                    "As a new minister of UN Ministry of Environment, what you have to do is installing affordable energy generators.",

            "Goal \n\n" +
                    "You have to control 4 elements: budget, energy production, pollution, and approval rate. ",

            "Goal \n\n" +
                    "In every turn, you will get budget according to your approval rate (100 M$ per 1%). " +
                    "By installing energy generators, you can increase energy production. ",

            "Goal \n\n" +
                    "If your answer of the quiz (will be explained later) is incorrect, pollution will be increased. " +
                    "Approval rate will be changed by whether you choose proper region for an energy generator. ",

            "Goal \n\n" +
                    "Your goal is to make energy production 10,000 Mtoe. If your budget become 0 or pollution become 100, you will be fired (game over). ",

            "Goal \n\n" +
                    "Also you must acheive your goal in 30 turns, or there will be no more place to install new generators (game over).",

            "1. Selecting Region \n\n" +
                    "First, you select where to install a new energy generator. " +
                    "There are 30 countries available and each has different environment. " +
                    "You should consider sunshine, wind speed, precipitation, etc.",

            "2. Selecting Energy Generator \n\n" +
                    "Second, you select which energy generator to install. " +
                    "There are photovoltaics, solar thermal, wind power generator. " +
                    "You must select generator matching to the region.",

            "3. Getting New Energy Generator \n\n" +
                    "At first, you can use only photovoltaics generator. " +
                    "In the lab, you can get a new generator by solving the quiz. " +
                    "The lab will be opened according to your approval rate.",

            "4. Result Page \n\n" +
                    "Changes of the budget, energy production, pollution, and approval rate will be showed in the result page. " +
                    "Budget change will include new budget and the cost of the generator."
    )
    var tutorialTextCount: MutableLiveData<Int> = MutableLiveData<Int>(0)

    private val statusRepository = StatusRepository(application)
    private val tileRepository = TileRepository(application)

    fun startGame1() {
        val newStatus = Status(1)
        statusRepository.insert(newStatus)
        tileRepository.delete(1)
    }
}