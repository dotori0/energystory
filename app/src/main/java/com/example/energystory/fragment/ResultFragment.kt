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
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import com.example.energystory.R
import com.example.energystory.viewmodel.GameViewModel

class  ResultFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_result, container, false)
        val viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(GameViewModel::class.java)
        viewModel.gameResult.value = 0

        val budgetChange: TextView = view.findViewById(R.id.budget_change)
        val energyChange: TextView = view.findViewById(R.id.energy_change)
        val pollutionChange: TextView = view.findViewById(R.id.pollution_change)
        val approvalRateChange: TextView = view.findViewById(R.id.approval_rate_change)

        val nextButton: Button = view.findViewById(R.id.next_button4)

        viewModel.changedStatus.observe(viewLifecycleOwner, Observer {
            fun changeResult(textView: TextView, data: Int, unit: String) {
                val blue = resources.getColor(R.color.better_blue)
                val red = resources.getColor(R.color.worse_red)

                if (data >= 0) {
                    textView.text = "+" + data.toString() + unit
                    textView.setTextColor(blue)
                } else {
                    textView.text = data.toString() + unit
                    textView.setTextColor(red)
                }
            }
            fun changeResult(textView: TextView, data: Float, unit: String) {
                val blue = resources.getColor(R.color.better_blue)
                val red = resources.getColor(R.color.worse_red)

                if (data >= 0) {
                    textView.text = "+" + data.toString() + unit
                    textView.setTextColor(blue)
                } else {
                    textView.text = data.toString() + unit
                    textView.setTextColor(red)
                }
            }

            changeResult(budgetChange, it.budget, " M$")
            changeResult(energyChange, it.energy, " Mtoe")
            changeResult(pollutionChange, it.pollution, "%")
            changeResult(approvalRateChange, it.approvalRate.toFloat() / 10, "%")
        })

        viewModel.result()

        viewModel.gameResult.observe(viewLifecycleOwner, Observer {
            if(it == 1)
                nextButton.setOnClickListener {
                    Navigation.findNavController(it).navigate(R.id.action_resultFragment_to_gameoverFragment)
                }
            else if(it == 2)
                nextButton.setOnClickListener {
                    Navigation.findNavController(it).navigate(R.id.action_resultFragment_to_gamewinFragment)
                }
            else if(it == 0)
                nextButton.setOnClickListener {
                    Navigation.findNavController(it).navigate(R.id.action_resultFragment_to_tileFragment)
                }
        })

        val budgetValue: TextView = view.findViewById(R.id.budget_value)
        val energyValue: TextView = view.findViewById(R.id.energy_value)
        val pollutionValue: TextView = view.findViewById(R.id.pollution_value)
        val approvalRateValue: TextView = view.findViewById(R.id.approval_rate_value)

        viewModel.getStatus().observe(viewLifecycleOwner, Observer {
            budgetValue.text = it.budget.toString() + " M$"
            if (it.budget <= 0) {
                budgetValue.setTextColor(resources.getColor(R.color.worse_red))
            }

            energyValue.text = it.energy.toString() + " Mtoe"
            if (it.energy >= 10000)
                energyValue.setTextColor(resources.getColor(R.color.worse_red))

            pollutionValue.text = it.pollution.toString() + "%"
            approvalRateValue.text = (it.approvalRate.toFloat() / 10).toString() + "%"
        })

        return view
    }
}