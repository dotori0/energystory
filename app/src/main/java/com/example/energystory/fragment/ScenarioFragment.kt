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

class ScenarioFragment: Fragment() {
    private lateinit var scenarioEp1Button: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_scenario, container, false)

        ProcessDatabase.getInstance(requireContext())!!.processDao().addProcess(Process(1, false, false))
        val ep1_process: Process? = ProcessDatabase.getInstance(requireContext())!!.processDao().findProcess(1)

        scenarioEp1Button = view.findViewById(R.id.scenario_ep1_button)
        scenarioEp1Button.setOnClickListener {
            if (ep1_process!!.tutorialDone)
                Navigation.findNavController(it).navigate(R.id.action_scenarioFragment_to_tileFragment) // require change
            else if (ep1_process.storyDone)
                Navigation.findNavController(it).navigate(R.id.action_scenarioFragment_to_tutorialFragment)
            else
                Navigation.findNavController(it).navigate(R.id.action_scenarioFragment_to_storyFragment)
        }
        return view
    }
}