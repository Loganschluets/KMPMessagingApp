package com.example.networkingapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.*
import com.example.networkingapp.android.databinding.ActivityMainBinding
import com.example.networkingapp.viewmodel.MainActivityViewModel
import androidx.lifecycle.viewmodel.*

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}

/*
@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}*/
