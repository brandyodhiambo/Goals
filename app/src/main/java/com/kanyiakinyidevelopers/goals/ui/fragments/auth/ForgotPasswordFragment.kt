package com.kanyiakinyidevelopers.goals.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.kanyiakinyidevelopers.goals.R
import com.kanyiakinyidevelopers.goals.databinding.FragmentForgotPasswordBinding
import com.kanyiakinyidevelopers.goals.utils.EventObserver
import com.kanyiakinyidevelopers.goals.utils.Resource
import com.kanyiakinyidevelopers.goals.utils.hideKeyboard
import com.kanyiakinyidevelopers.goals.utils.showSnackbar
import com.kanyiakinyidevelopers.goals.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentForgotPasswordBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater,container,false)


        subscribeToObserver()
        binding.ForgotPasswordButton.setOnClickListener {
            viewModel.forgotPassword(
                binding.ForgotPasswordEmail.editText?.text.toString()
            )
            this.hideKeyboard()
        }

        return binding.root
    }
    fun subscribeToObserver(){
        viewModel.forgotPass.observe(viewLifecycleOwner,EventObserver(
            onError = {
                this.showSnackbar(it)
                Toast.makeText(requireContext(), "An error has occurred, try again", Toast.LENGTH_SHORT).show()
            },
            onLoading = {

            }
        )
        {
            Toast.makeText(
                requireContext(),
                "A reset link has been sent to your email address",
                Toast.LENGTH_LONG
            ).show()
            showSnackbar("A reset link has been sent to your email address")
        }
        )
    }


}