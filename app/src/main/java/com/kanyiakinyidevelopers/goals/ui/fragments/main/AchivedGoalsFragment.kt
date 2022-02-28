package com.kanyiakinyidevelopers.goals.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kanyiakinyidevelopers.goals.adapters.AchivedHistoryAdopter
import com.kanyiakinyidevelopers.goals.databinding.FragmentAchivedGoalsBinding
import com.kanyiakinyidevelopers.goals.models.Goal
import com.kanyiakinyidevelopers.goals.utils.Resource
import com.kanyiakinyidevelopers.goals.utils.showSnackbar
import com.kanyiakinyidevelopers.goals.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.ArrayList


@AndroidEntryPoint
class AchivedGoalsFragment : Fragment() {
   private lateinit var binding: FragmentAchivedGoalsBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var achievedGoalsAdapter: AchivedHistoryAdopter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAchivedGoalsBinding.inflate(inflater,container,false)

        binding.swipeAchievedLayout.isRefreshing = false

        achievedGoalsAdapter = AchivedHistoryAdopter(AchivedHistoryAdopter.OnClickListener{ goal ->
            ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    //val goal = achievedGoalsAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.onGoalDeleted(goal)
                }

            }).attachToRecyclerView(binding.allAchivedGoals)
        })

        subscribeToAllAchievedGoalsObserver()



        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.goalChannel.collect { event->
                when(event){
                    is MainViewModel.GoalEvent.showUndoDeletedMessage->{
                        Snackbar.make(requireView(),"Deleted Goal",Snackbar.LENGTH_LONG)
                            .setAction("Undo"){
                                viewModel.onUndo(event.goal)
                            }.show()
                    }

                }
            }
        }

        return binding.root
    }

    private fun subscribeToAllAchievedGoalsObserver(){
        viewModel.achievedGoalsStatus.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading ->{
                    binding.progressBar.isVisible = true
                    binding.allAchivedGoals.isVisible = false
                }
                is Resource.Success ->{
                    binding.progressBar.isVisible = false
                    if (it.data?.isEmpty()!!) {
                        Toast.makeText(requireContext(), "No Achieved Goals", Toast.LENGTH_SHORT).show()
                        binding.swipeAchievedLayout.isRefreshing = false

                    }

                    binding.swipeAchievedLayout.isRefreshing = false
                    achievedGoalsAdapter.submitList(it.data)
                    binding.allAchivedGoals.adapter = achievedGoalsAdapter
                    binding.allAchivedGoals.isVisible = true
                }
                is Resource.Error ->{
                    binding.progressBar.isVisible = false
                    showSnackbar(it.message!!)
                    binding.swipeAchievedLayout.isRefreshing = false

                }
                else ->{
                    Toast.makeText(requireContext(), "Unknown Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}