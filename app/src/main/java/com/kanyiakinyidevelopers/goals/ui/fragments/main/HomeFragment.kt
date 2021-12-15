package com.kanyiakinyidevelopers.goals.ui.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kanyiakinyidevelopers.goals.R
import com.kanyiakinyidevelopers.goals.adapters.AchievedGoalsAdapter
import com.kanyiakinyidevelopers.goals.adapters.GoalsAdapter
import com.kanyiakinyidevelopers.goals.databinding.FragmentHomeBinding
import com.kanyiakinyidevelopers.goals.utils.EventObserver
import com.kanyiakinyidevelopers.goals.utils.Resource
import com.kanyiakinyidevelopers.goals.utils.showSnackbar
import com.kanyiakinyidevelopers.goals.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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

        binding.txtViewAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_achivedGoalsFragment)
        }



        subscribeToGoalsObserver()
        subscribeToAchievedGoalsObserver()

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
        viewModel.goalsStatus.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading ->{
                    binding.shimmerEffectTwo.root.isVisible = true
                    binding.allGoalsRecyclerView.isVisible = false

                }
                is Resource.Success ->{
                    if (it.data?.isEmpty()!!) {
                        Toast.makeText(requireContext(), "No Goals", Toast.LENGTH_SHORT).show()
                        binding.swipeLayout.isRefreshing = false

                    }
                    binding.shimmerEffectTwo.root.isVisible = false
                    binding.swipeLayout.isRefreshing = false
                    goalsAdapter.submitList(it.data)
                    binding.allGoalsRecyclerView.adapter = goalsAdapter
                    binding.allGoalsRecyclerView.isVisible = true
                }
                is Resource.Error ->{
                    showSnackbar(it.message!!)
                    binding.shimmerEffectTwo.root.isVisible = true
                    binding.swipeLayout.isRefreshing = false
                }
                else -> {
                    Toast.makeText(requireContext(), "Unknown Error", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun subscribeToAchievedGoalsObserver() {
        viewModel.achievedGoalsStatus.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading ->{
                    binding.shimmerOne.root.isVisible = true
                    binding.achievedGoalsRecyclerView.isVisible = false
                }
                is Resource.Success ->{
                    if (it.data?.isEmpty()!!) {
                        Toast.makeText(requireContext(), "No Achieved Goals", Toast.LENGTH_SHORT).show()
                        binding.swipeLayout.isRefreshing = false
                    }
                    binding.shimmerOne.root.isVisible = false
                    binding.swipeLayout.isRefreshing = false

                    achievedGoalsAdapter.submitList(it.data)
                    binding.achievedGoalsRecyclerView.adapter = achievedGoalsAdapter
                    binding.achievedGoalsRecyclerView.isVisible = true

                    Timber.d("${it.data}")
                }
                is Resource.Error ->{
                    showSnackbar(it.message!!)
                    binding.swipeLayout.isRefreshing = false
                    binding.shimmerOne.root.isVisible = true
                }
                else -> {
                    Toast.makeText(requireContext(), "Unknown Error", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}
