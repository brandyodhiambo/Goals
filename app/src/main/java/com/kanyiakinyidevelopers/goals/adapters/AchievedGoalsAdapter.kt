package com.kanyiakinyidevelopers.goals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kanyiakinyidevelopers.goals.databinding.AchievedRowBinding
import com.kanyiakinyidevelopers.goals.models.Goal

class AchievedGoalsAdapter :
    ListAdapter<Goal, AchievedGoalsAdapter.MyViewHolder>(COMPARATOR) {

    private object COMPARATOR : DiffUtil.ItemCallback<Goal>() {
        override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean {
            return oldItem.dateTime == newItem.dateTime
        }

    }

    inner class MyViewHolder(private val binding: AchievedRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(goal: Goal?) {
            binding.textViewATitle.text = goal?.goalTitle
            binding.textViewADesc.text = goal?.goalDescription
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            AchievedRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val goal = getItem(position)
        holder.bind(goal)


    }
}
