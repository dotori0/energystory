package com.example.energystory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.energystory.R
import com.example.energystory.viewmodel.QuizViewModel

class QuizStoryFragment: Fragment() {
    lateinit var speechNextButton: Button
    lateinit var screenNextButton: Button
    lateinit var speechPrevButton: Button
    lateinit var quizStoryText: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_quiz_story, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)

        speechNextButton = view.findViewById(R.id.next_button2)
        screenNextButton = view.findViewById(R.id.next_button3)
        speechPrevButton = view.findViewById(R.id.prev_button)
        quizStoryText = view.findViewById(R.id.quiz_story_text)

        viewModel.storyTextArray = when(viewModel.getEnergyGeneratorNum()) {
            1 -> viewModel.storyTextArray1
            2 -> viewModel.storyTextArray2
            else -> emptyArray()
        }

        speechNextButton.setOnClickListener {
            viewModel.storyTextCount.setValue(viewModel.storyTextCount.value?.plus(1))
        }

        speechPrevButton.setOnClickListener {
            viewModel.storyTextCount.setValue(viewModel.storyTextCount.value?.minus(1))
        }


        screenNextButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_quizStoryFragment_to_quizFragment)
            viewModel.storyTextCount.value = 0
        }


        viewModel.storyTextCount.observe(viewLifecycleOwner, Observer {
            if (it == 0) {
                speechPrevButton.visibility = View.GONE
            }
            else {
                speechPrevButton.visibility = View.VISIBLE
            }
            if (it + 1 == viewModel.storyTextArray.size) {
                speechNextButton.visibility = View.GONE
                screenNextButton.visibility = View.VISIBLE
            }
            else {
                speechNextButton.visibility = View.VISIBLE
            }
            quizStoryText.text = viewModel.storyTextArray[it]
        })

        return view
    }
}