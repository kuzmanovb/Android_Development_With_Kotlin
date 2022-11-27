package com.example.kotlinexamproject.api

import com.example.kotlinexamproject.models.CryptocurrencyModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("coins/markets?vs_currency=usd")
    fun getCryptocurrencies(): Call<List<CryptocurrencyModel>>

}