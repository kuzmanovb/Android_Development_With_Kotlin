package com.example.kotlinexamproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.kotlinexamproject.R
import com.example.kotlinexamproject.adapters.CryptocurrencyAdapter
import com.example.kotlinexamproject.api.ApiRepository
import com.example.kotlinexamproject.api.ApiService
import com.example.kotlinexamproject.databinding.FragmentCryptocurrencyDetailsBinding
import com.example.kotlinexamproject.ui.MainActivity
import com.example.kotlinexamproject.viewModels.CryptocurrencyViewModel
import com.example.kotlinexamproject.viewModels.CryptocurrencyViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CryptocurrencyDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCryptocurrencyDetailsBinding
    private lateinit var viewModel: CryptocurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCryptocurrencyDetailsBinding.inflate(inflater, container, false)
        init()
        observeData()
        setFavourite()
        val cryptocurrencyName = arguments?.getString("cryptocurrencyName").toString()
        viewModel.getCryptocurrencyByName(cryptocurrencyName)



        return binding.root
    }

    private fun init() {
        val apiService = (activity as MainActivity).retrofit.create(ApiService::class.java)
        val countryRepository = ApiRepository(apiService)

        val factory =
            CryptocurrencyViewModelFactory(countryRepository, (activity as MainActivity).db)
        viewModel = ViewModelProvider(this, factory)[CryptocurrencyViewModel::class.java]
    }

    private fun observeData() {

        GlobalScope.launch {
            viewModel.currentCryptocurrency.collect {
                activity?.runOnUiThread {
                    binding.cryptocurrency = it

                    (activity as? MainActivity)?.runOnUiThread {

                        Glide
                            .with(context ?: return@runOnUiThread)
                            .load(it?.image)
                            .centerCrop()
                            .placeholder(R.drawable.no_image_avavailable)
                            .into(binding.cryptocurrencyFlag)
                    }
                }
            }
        }

        GlobalScope.launch {

            viewModel.changeIsFavourite.collect{
                activity?.runOnUiThread {
                    if (it) {
                        binding.btnLike.setImageResource(android.R.drawable.star_big_on)
                    } else {
                        binding.btnLike.setImageResource(android.R.drawable.star_off)
                    }
                }
            }
        }
    }

    private fun setFavourite(){

        binding.btnLike.setOnClickListener {
            val cryptocurrencyName = arguments?.getString("cryptocurrencyName").toString()
            viewModel.setCryptocurrencyAsFavourite(cryptocurrencyName)
        }

    }
}