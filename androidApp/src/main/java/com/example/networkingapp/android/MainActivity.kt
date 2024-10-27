package com.example.networkingapp.android

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.*
import com.example.networkingapp.android.databinding.ActivityMainBinding
import com.example.networkingapp.viewmodel.MainActivityViewModel
import androidx.lifecycle.viewmodel.*

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    var context = applicationContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        configureLayout()


    }


    fun configureLayout(){
        binding.sendBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {

                //make request
                viewModel.sendHttpRequest()

                Toast.makeText(context, "Request sent", Toast.LENGTH_LONG)
                    .show()

            }

        })
    }

    fun networkRequest(){

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
