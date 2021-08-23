package com.example.energystory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.energystory.R
import com.example.energystory.data.tile.Tile
import com.example.energystory.viewmodel.GameViewModel

class TileFragment: Fragment() {
    var blankCount = 0
    lateinit var viewModel: GameViewModel

    override fun onResume() {
        super.onResume()

        if (blankCount >= 30) {
            viewModel.restartGame1()
            Navigation.findNavController(requireView()).navigate(R.id.action_tileFragment_to_gameoverFragment)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_tile, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)

        val budgetValue: TextView = view.findViewById(R.id.budget_value)
        val energyValue: TextView = view.findViewById(R.id.energy_value)
        val pollutionValue: TextView = view.findViewById(R.id.pollution_value)
        val approvalRateValue: TextView = view.findViewById(R.id.approval_rate_value)
        val flagImage: ImageView = view.findViewById(R.id.flag_image)

        viewModel.getStatus().observe(viewLifecycleOwner, Observer {
            budgetValue.text = it.budget.toString() + " M$"
            energyValue.text = it.energy.toString() + " Mtoe"
            pollutionValue.text = it.pollution.toString() + "%"
            approvalRateValue.text = (it.approvalRate.toFloat() / 10).toString() + "%"
        })

        val card: CardView = view.findViewById(R.id.info_card)
        val nextButton: Button = view.findViewById(R.id.next_button5)
        nextButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_tileFragment_to_energyGeneratorFragment)
        }

        val countryName: TextView = view.findViewById(R.id.country_name)


        val sunLightValue: TextView = view.findViewById(R.id.sun_light_value)
        val sunPowerValue: TextView = view.findViewById(R.id.sun_power_value)
        val precipitationValue: TextView = view.findViewById(R.id.precipitation_value)
        val windValue: TextView = view.findViewById(R.id.wind_value)

        var tiles: MutableList<ImageView> = mutableListOf()

        tiles.add(view.findViewById(R.id.tile_1))
        tiles.add(view.findViewById(R.id.tile_2))
        tiles.add(view.findViewById(R.id.tile_3))
        tiles.add(view.findViewById(R.id.tile_4))
        tiles.add(view.findViewById(R.id.tile_5))
        tiles.add(view.findViewById(R.id.tile_6))
        tiles.add(view.findViewById(R.id.tile_7))
        tiles.add(view.findViewById(R.id.tile_8))
        tiles.add(view.findViewById(R.id.tile_9))
        tiles.add(view.findViewById(R.id.tile_10))
        tiles.add(view.findViewById(R.id.tile_11))
        tiles.add(view.findViewById(R.id.tile_12))
        tiles.add(view.findViewById(R.id.tile_13))
        tiles.add(view.findViewById(R.id.tile_14))
        tiles.add(view.findViewById(R.id.tile_15))
        tiles.add(view.findViewById(R.id.tile_16))
        tiles.add(view.findViewById(R.id.tile_17))
        tiles.add(view.findViewById(R.id.tile_18))
        tiles.add(view.findViewById(R.id.tile_19))
        tiles.add(view.findViewById(R.id.tile_20))
        tiles.add(view.findViewById(R.id.tile_21))
        tiles.add(view.findViewById(R.id.tile_22))
        tiles.add(view.findViewById(R.id.tile_23))
        tiles.add(view.findViewById(R.id.tile_24))
        tiles.add(view.findViewById(R.id.tile_25))
        tiles.add(view.findViewById(R.id.tile_26))
        tiles.add(view.findViewById(R.id.tile_27))
        tiles.add(view.findViewById(R.id.tile_28))
        tiles.add(view.findViewById(R.id.tile_29))
        tiles.add(view.findViewById(R.id.tile_30))

        blankCount = 0

        for (i: Int in 1..30) {
            val exist = viewModel.getTile(i)

            if (exist == null) {
                tiles[i - 1].setOnClickListener {
                    viewModel.selected_country = i
                    card.visibility = VISIBLE

                    val country = viewModel.countries[i - 1]

                    countryName.text = country.Name

                    viewModel.sunLight = country.sunLight
                    viewModel.sunPower = country.sunPower
                    viewModel.precipitation = country.precipitation
                    viewModel.wind = country.wind

                    sunLightValue.text = "sun light: " + country.sunLight.toString()
                    sunPowerValue.text = "sun power: " + country.sunPower.toString()
                    precipitationValue.text = "precipitation: " + country.precipitation.toString()
                    windValue.text = "wind: " + country.wind.toString()

                    flagImage.setImageDrawable(tiles[i - 1].drawable)
                    flagImage.clipToOutline = true
                }
            }
            else {
                blankCount += 1
                tiles[i - 1].visibility = INVISIBLE
            }
        }

        return view
    }
}
