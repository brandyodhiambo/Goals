package com.kanyiakinyidevelopers.goals.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kanyiakinyidevelopers.goals.adapters.AchievedGoalsAdapter
import com.kanyiakinyidevelopers.goals.adapters.AchivedHistoryAdopter
import com.kanyiakinyidevelopers.goals.databinding.FragmentAchivedGoalsBinding
import com.kanyiakinyidevelopers.goals.models.Goal
import com.kanyiakinyidevelopers.goals.utils.Resource
import com.kanyiakinyidevelopers.goals.utils.showSnackbar
import com.kanyiakinyidevelopers.goals.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AchivedGoalsFragment : Fragment() {
   private lateinit var binding: FragmentAchivedGoalsBinding
    private val viewModel: MainViewModel by viewModels()
    private val achievedGoalsAdapter: AchivedHistoryAdopter by lazy {
        AchivedHistoryAdopter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAchivedGoalsBinding.inflate(inflater,container,false)

        subscribeToAllAchievedGoalsObserver()

        viewModel.getAchievedGoals()
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
                    }

                    achievedGoalsAdapter.submitList(it.data)
                    binding.allAchivedGoals.adapter = achievedGoalsAdapter
                    binding.allAchivedGoals.isVisible = true
                }
                is Resource.Error ->{
                    binding.progressBar.isVisible = false
                    showSnackbar(it.message!!)
                }
                else ->{
                    Toast.makeText(requireContext(), "Unknown Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}