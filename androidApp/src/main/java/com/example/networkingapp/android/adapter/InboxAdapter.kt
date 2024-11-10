package com.example.networkingapp.android.adapter


import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.networkingapp.android.databinding.InboxItemBinding
import com.example.networkingapp.model.ReceivedMessageDto

class InboxAdapter(private val context: Context, private val foodItemList:MutableList<ReceivedMessageDto>)
    : RecyclerView.Adapter<InboxAdapter.InboxItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InboxItemViewHolder {
        val binding = InboxItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return InboxItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InboxItemViewHolder, position: Int) {
        val foodItem = foodItemList[position]
        holder.bind(foodItem)
    }

    override fun getItemCount(): Int {
        return foodItemList.size
    }


    inner class InboxItemViewHolder(inboxItemLayoutBinding: InboxItemBinding)
        : RecyclerView.ViewHolder(inboxItemLayoutBinding.root){

        private val binding = inboxItemLayoutBinding

        fun bind(messageObject: ReceivedMessageDto){
            binding.message.text = messageObject.message
            binding.sender.text = messageObject.sender
        }

    }
}