package com.example.energystory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.energystory.R
import com.example.energystory.data.process.Process
import com.example.energystory.data.process.ProcessDatabase

class GamewinFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_gamewin, container, false)

        val restartButton: Button = view.findViewById(R.id.restart_button2)
        restartButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_gamewinFragment_to_titleFragment)
        }

        return view
    }
}