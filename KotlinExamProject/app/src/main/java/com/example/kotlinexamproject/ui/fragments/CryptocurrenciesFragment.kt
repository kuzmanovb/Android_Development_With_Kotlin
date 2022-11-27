package com.example.kotlinexamproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinexamproject.api.ApiRepository
import com.example.kotlinexamproject.api.ApiService
import com.example.kotlinexamproject.adapters.CryptocurrencyAdapter
import com.example.kotlinexamproject.databinding.FragmentCryptocurrenciesBinding
import com.example.kotlinexamproject.ui.MainActivity
import com.example.kotlinexamproject.viewModels.CryptocurrencyViewModel
import com.example.kotlinexamproject.viewModels.CryptocurrencyViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CryptocurrenciesFragment : Fragment() {

    private lateinit var binding: FragmentCryptocurrenciesBinding
    private lateinit var viewModel: CryptocurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCryptocurrenciesBinding.inflate(inflater, container, false)
        init()
        observeData()
        viewModel.getCryptocurrencies()

//        binding.button.setOnClickListener {
//            var action = CryptocurrenciesFragmentDirections.actionFirstFragmentToSecondFragment()
//            findNavController().navigate(action)
//        }

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
            viewModel.cryptocurrencies.collect {
                activity?.runOnUiThread {
                    val cryptocurrencies = it.sortedBy { it.market_cap }.sortedByDescending { it.isFavourite }
                    val adapter = CryptocurrencyAdapter(cryptocurrencies)

                    binding.cryptocurrenciesList.adapter = adapter
                    binding.cryptocurrenciesCount.text = "Cryptocurrencies: ${it.size}"
                }
            }
        }
    }

}


