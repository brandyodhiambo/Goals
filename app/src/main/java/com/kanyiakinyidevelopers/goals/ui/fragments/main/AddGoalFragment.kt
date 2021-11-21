package com.kanyiakinyidevelopers.goals.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.kanyiakinyidevelopers.goals.R
import com.kanyiakinyidevelopers.goals.databinding.FragmentAddGoalBinding
import com.kanyiakinyidevelopers.goals.utils.EventObserver
import com.kanyiakinyidevelopers.goals.utils.hideKeyboard
import com.kanyiakinyidevelopers.goals.utils.showSnackbar
import com.kanyiakinyidevelopers.goals.viewmodels.AuthViewModel
import com.kanyiakinyidevelopers.goals.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddGoalFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentAddGoalBinding
    private val viewModel: MainViewModel by viewModels()


    private var bgColor: String = "#FFFFFF"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddGoalBinding.inflate(layoutInflater,container,false)

        subscribeToObserver()

        binding.imageViewOrange.setOnClickListener(this)
        binding.imageViewBlue.setOnClickListener(this)
        binding.imageViewPink.setOnClickListener(this)

        binding.saveGoal.setOnClickListener {
            viewModel.addGoal(
                binding.textInputLayoutTitle.editText?.text.toString(),
                binding.textInputLayoutDesc.editText?.text.toString(),
                bgColor
            )

            this.hideKeyboard()
        }


        return binding.root
    }

    private fun subscribeToObserver(){
        viewModel.addGoalStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                this.showSnackbar(it)
                binding.progressBarSaveGoal.isVisible=false
            },
            onLoading = {
                binding.progressBarSaveGoal.isVisible=true
            }
        ){
            binding.progressBarSaveGoal.isVisible = false
            this.showSnackbar("Goal added Successful")
        })
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.imageViewOrange -> {
                Toast.makeText(requireContext(), "Orange", Toast.LENGTH_SHORT).show()
                bgColor = "#ffc04d"
            }
            binding.imageViewBlue -> {
                Toast.makeText(requireContext(), "Blue", Toast.LENGTH_SHORT).show()
                bgColor = "#4d4dff"
            }
            binding.imageViewPink -> {
                Toast.makeText(requireContext(), "Pink", Toast.LENGTH_SHORT).show()
                bgColor = "#ec9797"
            }
        }
    }
}