package net.gostartups.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.gostartups.myapplication.databinding.FragmentCountryDescriptionBinding

class CountryDescriptionFragment : Fragment() {
    private lateinit var binding: FragmentCountryDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryDescriptionBinding.inflate(inflater, container, false)



        return binding.root
    }


}