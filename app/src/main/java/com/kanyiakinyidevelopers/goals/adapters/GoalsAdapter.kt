package com.kanyiakinyidevelopers.goals.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kanyiakinyidevelopers.goals.R
import com.kanyiakinyidevelopers.goals.databinding.GoalRowBinding
import com.kanyiakinyidevelopers.goals.models.Goal

class GoalsAdapter(private val onClickListener:OnClickListener) : ListAdapter<Goal, GoalsAdapter.MyViewHolder>(COMPARATOR) {

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

        val checkBox = itemView.findViewById<CheckBox>(R.id.checkBoxAchieved)
        @SuppressLint("Range")
        fun bind(goal: Goal?) {

            binding.textViewTitle.text = goal?.goalTitle
            binding.textViewDesc.text = goal?.goalDescription
            binding.checkBoxAchieved.isChecked = goal!!.isAchieved
            binding.textViewDate.text = goal.dateTime
            binding.textViewName.text = goal.poster

            val myColor: Int = Color.parseColor(goal.goalBgColor)

            binding.goalCard.setBackgroundColor(myColor)

            if (goal.goalBgColor.equals("#4d4dff")){
                binding.textViewTitle.setTextColor(Color.parseColor("#ffffff"))
                binding.textViewDesc.setTextColor(Color.parseColor("#ffffff"))
                binding.checkBoxAchieved.setTextColor(Color.parseColor("#ffffff"))
                binding.textViewDate.setTextColor(Color.parseColor("#ffffff"))
                binding.textViewName.setTextColor(Color.parseColor("#ffffff"))
            }
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
        holder.checkBox.setOnClickListener {
            onClickListener.onClick(goal)
        }
    }

    class OnClickListener(val clickListener: (goalModel: Goal) -> Unit) {
        fun onClick(goalModel: Goal) = clickListener(goalModel)
    }
}