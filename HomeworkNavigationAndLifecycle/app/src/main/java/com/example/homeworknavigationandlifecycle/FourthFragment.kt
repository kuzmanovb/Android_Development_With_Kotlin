package com.example.homeworknavigationandlifecycle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.homeworknavigationandlifecycle.databinding.FragmentFourthBinding

class FourthFragment : Fragment() {

    private lateinit var binding: FragmentFourthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFourthBinding.inflate(inflater, container, false)

        binding.buttonNextFragment.setOnClickListener {
            val action = FourthFragmentDirections.actionInFourthToFirst()
            findNavController().navigate(action)
        }

        return binding.root
    }
}