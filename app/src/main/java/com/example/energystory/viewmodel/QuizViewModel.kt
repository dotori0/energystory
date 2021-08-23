package com.example.energystory.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.energystory.data.status.StatusRepository

class QuizViewModel(application: Application): AndroidViewModel(application) {
    var storyTextArray: Array<String> = emptyArray()
    val storyTextArray1: Array<String> = arrayOf(
            "Hello. I am Doctor Park from Harvard University. " +
                    "You should solve a quiz hear to help the research. You already have photovoltaics generator. " +
                    "By solving the quiz, you will be able to use solar thermal energy generator.",

            "I am going to ask you 4 questions. Each is about scientific principle, advantage, disadvantage, and proper environment for the generator.",

            "If your answer is wrong, the pollution rate will be increased because you are the research will be delayed.",

            "If you are ready, start the quiz!"
    )
    val storyTextArray2: Array<String> = arrayOf(
            "Nice to see you again. Last time, you did very well. There was some progress in our research. " +
                    "We can now make a wind power generator.",

            "However, it is not perfect. You have to help us by solving the quiz again. I am going to ask you 4 questions just like previous time.",

            "If your answer is wrong, the pollution rate will be increased because you are the research will be delayed.",

            "If you are ready, start the quiz!"
    )

    var storyTextCount: MutableLiveData<Int> = MutableLiveData<Int>(0)

    fun getEnergyGeneratorNum(): Int {
        val statusRepository = StatusRepository(getApplication())
        return statusRepository.findSync(1).generatorNumber
    }


    data class Question(
            val text: String,
            val answers: List<String>
    )

    var questions: MutableList<Question> = mutableListOf()
    val questions1: MutableList<Question> = mutableListOf(
            Question(text = "¿Which is  the Unit in the S.I for Energy?",
                    answers = listOf("Jules", "Ampere", "Volt", "meter")),
            Question(text = "¿Carbon is a renewable natural resource?",
                    answers = listOf("False, Carbon is nonrenewable resource", "True", "False, Carbon is a type of Renewable Energy", "Carbon is not a natural resource")),
            Question(text = "Wind Energy is a alternative source of energy based on the use of wind?",
                    answers = listOf("True", "False", "False. It is based on the energy provided by sun", "True, It is based in the use of fosil fuels too")),
            Question(text = "According to scientists, about how many countries could run entirely on wind, solar and water power by 2050?",
                    answers = listOf("140", "70", "20", "0"))
    )
    val questions2: MutableList<Question> = mutableListOf(
            Question(text = "The source of energy that we can find in organic matter, vegetal and animal waste recives the name of",
                    answers = listOf("Biomass", "Geothermal", "tidal", "solar")),
            Question(text = "What influence has in wind energy if  the air speed that moves the wind turbines is increased?",
                    answers = listOf("Wind energy increases", "Wind energy reduces", "Wind energy remain constant", "wind turbines will destroyed owing to speed")),
            Question(text = "Across the globe, the coal industry is declining, while renewable energy industries are on the rise.",
                    answers = listOf("True", "False", "It can be measured", "I am not sure")),
            Question(text = "¿Which of the following is not considered to be a source of renewable energy?",
                    answers = listOf("Natural Gas", "Wind", "Hydropower", "Solar"))
    )
    var questionIndex = 0

    var answers: MutableList<String> = mutableListOf()
    var question: Question = Question("", listOf())

    fun randomizeQuestion() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    fun setQuestion() {
        question = questions[questionIndex++]
        answers = question.answers.toMutableList()
        answers.shuffle()
    }


}