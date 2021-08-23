package com.example.energystory.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.energystory.R
import com.example.energystory.data.process.Process
import com.example.energystory.data.process.ProcessDatabase
import com.example.energystory.data.status.Status
import com.example.energystory.data.status.StatusRepository
import com.example.energystory.viewmodel.GameViewModel
import com.example.energystory.viewmodel.QuizViewModel

class QuizFragment: Fragment() {
    lateinit var answerRadioButtons: List<RadioButton>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)
        val gameViewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)
        val pollutionValue: TextView = view.findViewById(R.id.pollution_value)

        viewModel.questions = when(viewModel.getEnergyGeneratorNum()) {
            1 -> viewModel.questions1
            2 -> viewModel.questions2
            else -> viewModel.questions
        }

        val radioGroup: RadioGroup = view.findViewById(R.id.questionRadioGroup)
        val question: TextView = view.findViewById(R.id.questionText)
        answerRadioButtons = listOf(
                view.findViewById(R.id.firstAnswerRadioButton),
                view.findViewById(R.id.secondAnswerRadioButton),
                view.findViewById(R.id.thirdAnswerRadioButton),
                view.findViewById(R.id.fourthAnswerRadioButton)
        )

        viewModel.randomizeQuestion()
        setAnswers(viewModel.answers)
        question.text = viewModel.question.text


        val submmitButton: Button = view.findViewById(R.id.submitButton)
        submmitButton.setOnClickListener {
            val checkId = radioGroup.checkedRadioButtonId

            if (-1 != checkId) {
                var answerIndex = when(checkId) {
                    R.id.firstAnswerRadioButton -> 0
                    R.id.secondAnswerRadioButton -> 1
                    R.id.thirdAnswerRadioButton -> 2
                    R.id.fourthAnswerRadioButton -> 3
                    else -> 0
                }

                if (viewModel.answers[answerIndex] == viewModel.question.answers[0]) {
                    if (viewModel.questionIndex == viewModel.questions.size) {
                        val oldStatus = gameViewModel.getStatusSync()
                        val newStatus = Status(1, oldStatus.budget, oldStatus.energy, oldStatus.approvalRate, oldStatus.pollution, oldStatus.generatorNumber + 1)
                        gameViewModel.changeStatus(newStatus)
                        Navigation.findNavController(it).navigate(R.id.action_quizFragment_to_energyGeneratorFragment)
                    } else {
                        viewModel.setQuestion()
                        setAnswers(viewModel.answers)
                        question.text = viewModel.question.text
                    }
                } else {
                    val oldStatus = gameViewModel.getStatusSync()
                    val newStatus = Status(1, oldStatus.budget, oldStatus.energy, oldStatus.approvalRate, oldStatus.pollution + 10, oldStatus.generatorNumber)

                    if (newStatus.pollution >= 100) {
                        val processDatabase = ProcessDatabase.getInstance(requireActivity())!!
                        processDatabase.processDao().updateProcess(Process(1, false, false))
                        Toast.makeText(requireContext(), "game over (pollution 100%)", LENGTH_LONG).show()
                        Navigation.findNavController(it).navigate(R.id.action_quizFragment_to_gameoverFragment)
                    } else {
                        Toast.makeText(requireContext(), "wrong answer (pollution +10%)", LENGTH_LONG).show()
                        gameViewModel.changeStatus(newStatus)
                        pollutionValue.setTextColor(resources.getColor(R.color.worse_red))
                    }
                }
            }
        }


        val budgetValue: TextView = view.findViewById(R.id.budget_value)
        val energyValue: TextView = view.findViewById(R.id.energy_value)
        val approvalRateValue: TextView = view.findViewById(R.id.approval_rate_value)

        gameViewModel.getStatus().observe(viewLifecycleOwner, Observer {
            budgetValue.text = it.budget.toString() + " M$"
            energyValue.text = it.energy.toString() + " Mtoe"
            pollutionValue.text = it.pollution.toString() + "%"
            approvalRateValue.text = (it.approvalRate.toFloat() / 10).toString() + "%"
        })

        return view
    }

    fun setAnswers(answers: List<String>) {
        for (i: Int in 0..(answers.size - 1)) {
            answerRadioButtons[i].text = answers[i]
        }
    }
}