package com.example.energystory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.energystory.viewmodel.GameViewModel
import com.example.energystory.viewmodel.QuizViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel1 = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(GameViewModel::class.java)
        val viewModel2 = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(QuizViewModel::class.java)
    }
}