package com.kanyiakinyidevelopers.goals.ui.fragments.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kanyiakinyidevelopers.goals.R
import com.kanyiakinyidevelopers.goals.databinding.FragmentLoginBinding
import com.kanyiakinyidevelopers.goals.ui.activities.MainActivity
import com.kanyiakinyidevelopers.goals.utils.EventObserver
import com.kanyiakinyidevelopers.goals.utils.hideKeyboard
import com.kanyiakinyidevelopers.goals.utils.showSnackbar
import com.kanyiakinyidevelopers.goals.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel:AuthViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentLoginBinding.inflate(layoutInflater,container,false)

        subscribeToObserver()
        binding.signIn.setOnClickListener {
            viewModel.loginUser(
                binding.textInputLayoutEmailSignIn.editText?.text.toString(),
                binding.textInputLayoutSignInPassword.editText?.text.toString()
            )
            this.hideKeyboard()
        }
        binding.forgotPass.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
        binding.txtSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        return binding.root
    }
    fun subscribeToObserver(){
        viewModel.loginStatus.observe(viewLifecycleOwner,EventObserver(
            onError = {
                this.showSnackbar(it)
                binding.SignInprogressBar.isVisible =false
            },
            onLoading = {
                binding.SignInprogressBar.isVisible = true
            }
        ){
            binding.SignInprogressBar.isVisible = false
            this.showSnackbar("Logged in Successfully")
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }

        )
    }




}