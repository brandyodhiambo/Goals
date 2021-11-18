package com.kanyiakinyidevelopers.goals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kanyiakinyidevelopers.goals.databinding.GoalRowBinding
import com.kanyiakinyidevelopers.goals.models.Goal

class GoalsAdapter : ListAdapter<Goal, GoalsAdapter.MyViewHolder>(COMPARATOR) {

    private object COMPARATOR : DiffUtil.ItemCallback<Goal>() {
        override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean {
            return oldItem.dateTime == newItem.dateTime
        }

    }

    inner class MyViewHolder(private val binding: GoalRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(goal: Goal?) {
            binding.textViewTitle.text = goal?.title
            binding.textViewDesc.text = goal?.description
            binding.checkBoxAchieved.isChecked = goal!!.isAchieved
            binding.textViewDate.text = goal.dateTime
            binding.textViewName.text = goal.poster
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            GoalRowBinding.inflate(
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