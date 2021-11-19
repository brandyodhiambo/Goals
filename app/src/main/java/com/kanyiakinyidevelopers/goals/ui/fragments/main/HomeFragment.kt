package com.kanyiakinyidevelopers.goals.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.kanyiakinyidevelopers.goals.R
import com.kanyiakinyidevelopers.goals.adapters.AchievedGoalsAdapter
import com.kanyiakinyidevelopers.goals.adapters.GoalsAdapter
import com.kanyiakinyidevelopers.goals.databinding.FragmentHomeBinding
import com.kanyiakinyidevelopers.goals.utils.EventObserver
import com.kanyiakinyidevelopers.goals.utils.showSnackbar
import com.kanyiakinyidevelopers.goals.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()

    private val goalsAdapter: GoalsAdapter by lazy {
        GoalsAdapter()
    }

    private val achievedGoalsAdapter: AchievedGoalsAdapter by lazy {
        AchievedGoalsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        subscribeToGoalsObserver()
        subscribeToAchievedGoalsObserver()

        return view
    }

    private fun subscribeToGoalsObserver() {
        viewModel.goalsStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                showSnackbar(it)
                //binding.goalsProgressbar.isVisible = false
            },
            onLoading = {
                //binding.goalsProgressbar.isVisible = true
            }
        ) { goalsList ->
            if (goalsList.isEmpty()) {
                Toast.makeText(requireContext(), "No Goals", Toast.LENGTH_SHORT).show()
            }
            //binding.goalsProgressbar.isVisible = false
            /*goalsAdapter.submitList(goalsList)
            binding.goalsRecyclerView.adapter = goalsAdapter*/
        })
    }

    private fun subscribeToAchievedGoalsObserver() {
        viewModel.achievedGoalsStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                showSnackbar(it)
                //binding.achievedGoalsProgressbar.isVisible = false
            },
            onLoading = {
                //binding.achievedGoalsProgressbar.isVisible = true
            }
        ) { achieveGoalsList ->
            if (achieveGoalsList.isEmpty()) {
                Toast.makeText(requireContext(), "No Achieved Goals", Toast.LENGTH_SHORT).show()
            }
            //binding.achievedGoalsProgressbar.isVisible = false
            /*achievedGoalsAdapter.submitList(achieveGoalsList)
            binding.goalsRecyclerView.adapter = achievedGoalsAdapter*/
        })
    }
}