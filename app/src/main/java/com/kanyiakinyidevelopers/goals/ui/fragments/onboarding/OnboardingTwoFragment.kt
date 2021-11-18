package com.kanyiakinyidevelopers.goals.ui.fragments.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.kanyiakinyidevelopers.goals.R
import com.kanyiakinyidevelopers.goals.utils.onBoardingDone

class OnboardingTwoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding_two, container, false)
        val viewPager: ViewPager2? = activity?.findViewById(R.id.viewPager2)
        val next2: TextView = view.findViewById(R.id.textViewNext2)
        val skip2: TextView = view.findViewById(R.id.textViewSkip2)

        next2.setOnClickListener {
            viewPager?.currentItem = 2
        }

        skip2.setOnClickListener {
            onBoardingDone(requireActivity())
            findNavController().navigate(R.id.action_viewPagerFragment_to_registerFragment)
        }

        return view
    }
}