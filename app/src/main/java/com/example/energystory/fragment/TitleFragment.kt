package com.example.energystory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.energystory.R

class TitleFragment : Fragment() {
    private lateinit var startButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_title, container, false)

        startButton = view.findViewById(R.id.start_button)
        startButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_titleFragment_to_scenarioFragment)
        }

        return view
    }
}