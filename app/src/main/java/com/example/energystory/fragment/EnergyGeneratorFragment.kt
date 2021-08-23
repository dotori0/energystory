package com.example.energystory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import com.example.energystory.R
import com.example.energystory.data.tile.Tile
import com.example.energystory.viewmodel.GameViewModel

class EnergyGeneratorFragment : Fragment() {
    private lateinit var windPowerLayout: ConstraintLayout
    private lateinit var solarThermalLayout: ConstraintLayout
    private lateinit var pvLayout: ConstraintLayout

    private lateinit var windPowerCard: CardView
    private lateinit var solarThermalCard: CardView
    private lateinit var pvCard: CardView

    private lateinit var windPowerSelectButton: Button
    private lateinit var solarThermalSelectButton: Button
    private lateinit var pvSelectbutton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_energy_generator, container, false)
        val viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(GameViewModel::class.java)

        windPowerLayout = view.findViewById(R.id.wind_power_layout)
        solarThermalLayout = view.findViewById(R.id.solar_thermal_layout)
        pvLayout = view.findViewById(R.id.pv_layout)

        windPowerCard = view.findViewById(R.id.wind_power_card_view)
        solarThermalCard = view.findViewById(R.id.solar_thermal_card_view)
        pvCard = view.findViewById(R.id.pv_card_view)

        windPowerSelectButton = view.findViewById(R.id.wind_power_select_button)
        solarThermalSelectButton = view.findViewById(R.id.solar_thermal_select_button)
        pvSelectbutton = view.findViewById(R.id.pv_select_button)

        windPowerLayout.setOnClickListener {
            windPowerCard.visibility = VISIBLE
            solarThermalCard.visibility = GONE
            pvCard.visibility = GONE

            windPowerLayout.setBackgroundResource(R.drawable.blue_edge)
            solarThermalLayout.setBackgroundResource(R.drawable.gray_edge)
            pvLayout.setBackgroundResource(R.drawable.gray_edge)

            viewModel.changeEnergyGeneratorType(3)
        }

        solarThermalLayout.setOnClickListener {
            windPowerCard.visibility = GONE
            solarThermalCard.visibility = VISIBLE
            pvCard.visibility = GONE

            windPowerLayout.setBackgroundResource(R.drawable.gray_edge)
            solarThermalLayout.setBackgroundResource(R.drawable.blue_edge)
            pvLayout.setBackgroundResource(R.drawable.gray_edge)

            viewModel.changeEnergyGeneratorType(2)
        }

        pvLayout.setOnClickListener {
            windPowerCard.visibility = GONE
            solarThermalCard.visibility = GONE
            pvCard.visibility = VISIBLE

            windPowerLayout.setBackgroundResource(R.drawable.gray_edge)
            solarThermalLayout.setBackgroundResource(R.drawable.gray_edge)
            pvLayout.setBackgroundResource(R.drawable.blue_edge)

            viewModel.changeEnergyGeneratorType(1)
        }

        val nextListener = View.OnClickListener {
            val newTile = Tile(0, 1, viewModel.selected_country)
            viewModel.insertTile(newTile)
            Navigation.findNavController(it).navigate(R.id.action_energyGeneratorFragment_to_resultFragment)
        }

        windPowerSelectButton.setOnClickListener(nextListener)
        solarThermalSelectButton.setOnClickListener(nextListener)
        pvSelectbutton.setOnClickListener(nextListener)

        viewModel.getStatus().observe(viewLifecycleOwner, Observer {
            if(it.generatorNumber >= 1)
                pvLayout.visibility = VISIBLE

            if(it.generatorNumber >= 2)
                solarThermalLayout.visibility = VISIBLE

            if(it.generatorNumber >= 3)
                windPowerLayout.visibility = VISIBLE
        })

        val budgetValue: TextView = view.findViewById(R.id.budget_value)
        val energyValue: TextView = view.findViewById(R.id.energy_value)
        val pollutionValue: TextView = view.findViewById(R.id.pollution_value)
        val approvalRateValue: TextView = view.findViewById(R.id.approval_rate_value)

        val labButton: ImageButton = view.findViewById(R.id.lab_button)

        viewModel.getStatus().observe(viewLifecycleOwner, Observer {
            budgetValue.text = it.budget.toString() + " M$"
            energyValue.text = it.energy.toString() + " Mtoe"
            pollutionValue.text = it.pollution.toString() + "%"
            approvalRateValue.text = (it.approvalRate.toFloat() / 10).toString() + "%"

            if (it.generatorNumber == 1 && it.approvalRate >= 112 || it.generatorNumber == 2 && it.approvalRate >= 132) {
                labButton.visibility = VISIBLE
            } else {
                labButton.visibility = GONE
            }
        })

        labButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_energyGeneratorFragment_to_quizStoryFragment)
        }

        return view
    }
}