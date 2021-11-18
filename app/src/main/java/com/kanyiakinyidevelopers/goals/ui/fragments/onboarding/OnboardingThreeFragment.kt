package com.kanyiakinyidevelopers.goals.ui.fragments.onboarding

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.kanyiakinyidevelopers.goals.R
import com.kanyiakinyidevelopers.goals.utils.onBoardingDone

class OnboardingThreeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_onboarding_three, container, false)
        val finish: TextView = view.findViewById(R.id.textViewNext3)

        finish.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_registerFragment)
            onBoardingDone(requireActivity())
        }

        return view
    }
}