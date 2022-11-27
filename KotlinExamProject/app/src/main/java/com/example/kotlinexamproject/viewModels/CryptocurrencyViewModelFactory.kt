package com.example.kotlinexamproject.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinexamproject.api.ApiRepository
import com.example.kotlinexamproject.db.AppDatabase

class CryptocurrencyViewModelFactory(private var apiRepository: ApiRepository, private var db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CryptocurrencyViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return  CryptocurrencyViewModel(apiRepository, db) as T
        }

        throw IllegalArgumentException ("UnknownViewModel")
    }
}