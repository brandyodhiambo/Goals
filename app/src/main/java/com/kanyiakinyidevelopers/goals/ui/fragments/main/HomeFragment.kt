package com.kanyiakinyidevelopers.goals.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kanyiakinyidevelopers.goals.R
import com.kanyiakinyidevelopers.goals.adapters.AchievedGoalsAdapter
import com.kanyiakinyidevelopers.goals.adapters.GoalsAdapter
import com.kanyiakinyidevelopers.goals.databinding.FragmentHomeBinding
import com.kanyiakinyidevelopers.goals.utils.Resource
import com.kanyiakinyidevelopers.goals.utils.showSnackbar
import com.kanyiakinyidevelopers.goals.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var goalsAdapter: GoalsAdapter

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

        goalsAdapter = GoalsAdapter(GoalsAdapter.OnClickListener { goal ->
            AlertDialog.Builder(requireContext())
                .setTitle("Marking Goal")
                .setMessage("Are you sure you have achieved this goal?")
                .setCancelable(false)
                .setPositiveButton(
                    "Yes"
                ) { _, _ ->

                    viewModel.markGoalAsAchieved(goal)

                    binding.swipeLayout.post {
                        binding.swipeLayout.isRefreshing = true
                        viewModel.getGoals()
                        viewModel.getAchievedGoals()
                    }

                    Toast.makeText(requireContext(), "Goal Marked as achieved", Toast.LENGTH_LONG)
                        .show()
                }
                .setNegativeButton("No", null)
                .show()

        })

        binding.txtViewAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_achivedGoalsFragment)
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addGoalFragment)
        }

        binding.swipeLayout.setOnRefreshListener {
            viewModel.getGoals()
            viewModel.getAchievedGoals()
        }

        return view
    }


    private fun subscribeToGoalsObserver() {
        viewModel.goalsStatus.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    binding.allGoalsRecyclerView.showShimmerAdapter()

                }
                is Resource.Success -> {
                    if (it.data?.isEmpty()!!) {
                        Toast.makeText(requireContext(), "No Goals", Toast.LENGTH_SHORT).show()
                        binding.swipeLayout.isRefreshing = false

                    }
                    binding.swipeLayout.isRefreshing = false
                    goalsAdapter.submitList(it.data)
                    binding.allGoalsRecyclerView.adapter = goalsAdapter
                    binding.allGoalsRecyclerView.hideShimmerAdapter()
                }
                is Resource.Error -> {
                    showSnackbar(it.message!!)
                    binding.allGoalsRecyclerView.showShimmerAdapter()
                    binding.swipeLayout.isRefreshing = false
                }
                else -> {
                    Toast.makeText(requireContext(), "Unknown Error", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun subscribeToAchievedGoalsObserver() {
        viewModel.achievedGoalsStatus.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    binding.achievedGoalsRecyclerView.showShimmerAdapter()
                }
                is Resource.Success -> {
                    if (it.data?.isEmpty()!!) {
                        Toast.makeText(requireContext(), "No Achieved Goals", Toast.LENGTH_SHORT)
                            .show()
                        binding.swipeLayout.isRefreshing = false
                    }
                    binding.swipeLayout.isRefreshing = false

                    achievedGoalsAdapter.submitList(it.data)
                    binding.achievedGoalsRecyclerView.adapter = achievedGoalsAdapter
                    binding.achievedGoalsRecyclerView.hideShimmerAdapter()

                    Timber.d("${it.data}")
                }
                is Resource.Error -> {
                    showSnackbar(it.message!!)
                    binding.swipeLayout.isRefreshing = false
                }
                else -> {
                    Toast.makeText(requireContext(), "Unknown Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}