package com.kanyiakinyidevelopers.goals.ui.fragments.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kanyiakinyidevelopers.goals.databinding.FragmentHelpBinding
import com.yesterselga.countrypicker.CountryPicker
import com.yesterselga.countrypicker.Theme


class HelpFragment : Fragment() {

    private lateinit var binding: FragmentHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHelpBinding.inflate(inflater,container,false)

        binding.tvappInstall.setOnClickListener {
            val uri = Uri.parse("https://www.wikihow.com/Install-Apps")
            startActivity(Intent(Intent.ACTION_VIEW,uri))
        }
        binding.tvfaq.setOnClickListener {
            val uri = Uri.parse("https://maybusch.com/5-smart-questions-achieve-your-goals/")
            startActivity(Intent(Intent.ACTION_VIEW,uri))
        }
        binding.tvCountry.setOnClickListener {
            val picker = CountryPicker.newInstance("Select Country", Theme.DARK)

            picker.setListener { name, code, dialCode, flagDrawableResID ->
                binding.tvCountry.text = name
               // YOUR_IMAGE_VIEW.setImageResource(flagDrawableResID)
                picker.dismiss()
            }
            picker.show(childFragmentManager, "COUNTRY_PICKER")
        }
        return binding.root
    }


}