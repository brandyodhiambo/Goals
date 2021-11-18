package com.kanyiakinyidevelopers.goals.ui.fragments.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.kanyiakinyidevelopers.goals.R

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val viewPager: ViewPager2 = view.findViewById(R.id.viewPager2)

        val fragmentList: ArrayList<Fragment> = arrayListOf(
            OnboardingOneFragment(),
            OnboardingTwoFragment(),
            OnboardingThreeFragment()
        )

        val adapter =
            ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        return view
    }
}