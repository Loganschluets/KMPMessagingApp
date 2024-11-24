package com.example.networkingapp.android

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.networkingapp.android.databinding.ActivityMainBinding
import com.example.networkingapp.viewmodel.MainActivityViewModel
import dev.icerock.moko.mvvm.getViewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.networkingapp.android.adapter.InboxAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    private lateinit var adapter: InboxAdapter

    private val username = "4"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel { MainActivityViewModel() }



        //test commit
        configureLayout()
        viewModel.isRequesting.ld().observe(this, Observer {
            if(it){
                binding.inboxList.visibility = View.GONE
                binding.progressBarCyclic.visibility = View.VISIBLE
            }else{
                createListAdapter()
                binding.progressBarCyclic.visibility = View.GONE
                binding.inboxList.visibility = View.VISIBLE
            }
        })

    }

    fun createListAdapter(){

        adapter = InboxAdapter(this, viewModel.messageList.value.toMutableList())
        binding.inboxList.adapter = adapter
        binding.inboxList.layoutManager = LinearLayoutManager(this)
    }


    fun configureLayout(){
        binding.sendBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {

                if(!binding.message.text.isNullOrEmpty() && !binding.recipient.text.isNullOrEmpty()) {
                    viewModel.sendMessage(username, binding.recipient.text.toString(),
                        binding.message.text.toString())
                }
            }
        })

        binding.getBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {

                viewModel.fetchMessages(username)
            }
        })

        binding.clearBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                viewModel.deleteMessages(username)
            }
        })
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
