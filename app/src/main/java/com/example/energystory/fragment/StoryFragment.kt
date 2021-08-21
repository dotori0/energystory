package com.example.energystory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.energystory.R
import com.example.energystory.data.process.ProcessDatabase
import com.example.energystory.data.process.Process

class StoryFragment : Fragment() {
    private lateinit var nextButton1: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_story, container, false)

        nextButton1 = view.findViewById(R.id.next_button1)
        nextButton1.setOnClickListener {
            val process = Process(1, true, false)
            ProcessDatabase.getInstance(requireContext())!!.processDao().updateProcess(process)

            Navigation.findNavController(it).navigate(R.id.action_storyFragment_to_tutorialFragment)
        }

        return view
    }
}