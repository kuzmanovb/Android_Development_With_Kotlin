package net.gostartups.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.gostartups.myapplication.databinding.FragmentCountriesBinding

class CountriesFragment : Fragment() {

   private lateinit var binding: FragmentCountriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountriesBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }
}