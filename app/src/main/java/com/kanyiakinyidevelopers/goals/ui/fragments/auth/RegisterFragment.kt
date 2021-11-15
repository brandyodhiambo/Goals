package com.kanyiakinyidevelopers.goals.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.kanyiakinyidevelopers.goals.R
import com.kanyiakinyidevelopers.goals.databinding.FragmentRegisterBinding
import com.kanyiakinyidevelopers.goals.utils.EventObserver
import com.kanyiakinyidevelopers.goals.utils.hideKeyboard
import com.kanyiakinyidevelopers.goals.utils.showSnackbar
import com.kanyiakinyidevelopers.goals.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel:AuthViewModel by viewModels()
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentRegisterBinding.inflate(layoutInflater,container,false)

        subscribeToObserver()

            binding.buttonSignup.setOnClickListener {
                viewModel.registerUsers(
                    binding.textInputLayoutName.editText?.text.toString(),
                    binding.textInputLayoutPhone.editText?.text.toString(),
                    binding.textInputLayoutEmail.editText?.text.toString(),
                    binding.textInputLayoutPassword.editText?.text.toString()
                )
                this.hideKeyboard()
            }
        return binding.root
    }
    private fun subscribeToObserver(){
        viewModel.registerStatus.observe(viewLifecycleOwner,EventObserver(
            onError = {
                this.showSnackbar(it)
                binding.progressBarRegister.isVisible=false
            },
            onLoading = {
                binding.progressBarRegister.isVisible=true
            }
        ){
           binding.progressBarRegister.isVisible = false
            this.showSnackbar("Registration Successful")
        })
    }
}