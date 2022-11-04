package net.gostartups.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryService {
    @GET("all")
    fun getCountries(): Call<List<Country>>

    @GET("name/{countryName}")
    fun getCountry(@Path(value = "countryName") countryName: String ): Call<Country>
}