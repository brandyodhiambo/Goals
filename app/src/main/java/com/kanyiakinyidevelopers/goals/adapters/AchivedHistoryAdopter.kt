package com.kanyiakinyidevelopers.goals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kanyiakinyidevelopers.goals.databinding.AchivedGoalsRowBinding
import com.kanyiakinyidevelopers.goals.models.Goal

class AchivedHistoryAdopter :ListAdapter<Goal,AchivedHistoryAdopter.MyHolder>(COMPARATOR){

    private object COMPARATOR : DiffUtil.ItemCallback<Goal>(){
        override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean {
            return oldItem.dateTime == newItem.dateTime
        }

    }

    inner class MyHolder(private val binding: AchivedGoalsRowBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(achived: Goal?) {
            binding.txtTitle.text = achived?.goalTitle
            binding.txtDescription.text = achived?.goalDescription
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(AchivedGoalsRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val achived = getItem(position)
        holder.bind(achived)
    }
}