package com.example.kotlinexamproject.api

import com.example.kotlinexamproject.models.CryptocurrencyModel
import retrofit2.Call

class ApiRepository constructor (
        private val apiService: ApiService
 ){
    fun getCryptocurrencies(): Call<List<CryptocurrencyModel>>? {
        return try {
            apiService.getCryptocurrencies()
        } catch (e: Exception) {
            // an error occurred, handle and act accordingly
            null
        }
    }


}