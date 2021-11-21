package com.kanyiakinyidevelopers.goals.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addGoalFragment)
        }

        return view
    }

    private fun subscribeToGoalsObserver() {
        viewModel.goalsStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                showSnackbar(it)
                binding.shimmerEffectTwo.root.isVisible = false
            },
            onLoading = {
                binding.shimmerEffectTwo.root.isVisible = true
            }
        ) { goalsList ->
            if (goalsList.isEmpty()) {
                Toast.makeText(requireContext(), "No Goals", Toast.LENGTH_SHORT).show()
            }
            binding.shimmerEffectTwo.root.isVisible = false
            goalsAdapter.submitList(goalsList)
            binding.allGoalsRecyclerView.adapter = goalsAdapter
            binding.allGoalsRecyclerView.isVisible = true
        })
    }

    private fun subscribeToAchievedGoalsObserver() {
        viewModel.achievedGoalsStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                showSnackbar(it)
                binding.shimmerOne.root.isVisible = false
            },
            onLoading = {
                binding.shimmerOne.root.isVisible = true
            }
        ) { achieveGoalsList ->
            if (achieveGoalsList.isEmpty()) {
                Toast.makeText(requireContext(), "No Achieved Goals", Toast.LENGTH_SHORT).show()
            }
            binding.shimmerOne.root.isVisible = false
            achievedGoalsAdapter.submitList(achieveGoalsList)
            binding.achievedGoalsRecyclerView.adapter = achievedGoalsAdapter
            binding.achievedGoalsRecyclerView.isVisible = true
        })
    }
}