package com.example.energystory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.energystory.R
import com.example.energystory.data.process.ProcessDatabase
import com.example.energystory.data.process.Process
import com.example.energystory.viewmodel.TutorialViewModel

class TutorialFragment : Fragment() {
    private lateinit var speechNextButton: Button
    private lateinit var screenNextButton: Button
    private lateinit var tutorialText: TextView
    private lateinit var speechPrevButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_tutorial, container, false)
        val viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(TutorialViewModel::class.java)

        speechNextButton = view.findViewById(R.id.next_button2)
        screenNextButton = view.findViewById(R.id.next_button3)
        speechPrevButton = view.findViewById(R.id.prev_button)
        tutorialText = view.findViewById(R.id.tutorial_text)

        speechNextButton.setOnClickListener {
            viewModel.tutorialTextCount.setValue(viewModel.tutorialTextCount.value?.plus(1))
        }

        speechPrevButton.setOnClickListener {
            viewModel.tutorialTextCount.setValue(viewModel.tutorialTextCount.value?.minus(1))
        }

        screenNextButton.setOnClickListener {
            val process = Process(1, true, true)
            ProcessDatabase.getInstance(requireContext())!!.processDao().updateProcess(process)
            viewModel.startGame1()

            Navigation.findNavController(it).navigate(R.id.action_tutorialFragment_to_tileFragment)
        }

        viewModel.tutorialTextCount.observe(viewLifecycleOwner, Observer {
            if (it == 0) {
                speechPrevButton.visibility = GONE
            }
            else {
                speechPrevButton.visibility = VISIBLE
            }
            if (it + 1 == viewModel.tutorialTextArray.size) {
                speechNextButton.visibility = GONE
                screenNextButton.visibility = VISIBLE
            }
            else {
                speechNextButton.visibility = VISIBLE
            }
            tutorialText.text = viewModel.tutorialTextArray[it]
        })

        return view
    }
}