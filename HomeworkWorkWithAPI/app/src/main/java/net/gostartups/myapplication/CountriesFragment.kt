package net.gostartups.myapplication

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.room.ColumnInfo
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.gostartups.myapplication.databinding.FragmentCountriesBinding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountriesFragment : Fragment() {

    private lateinit var binding: FragmentCountriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountriesBinding.inflate(inflater, container, false)

        // database dao
        val mainActivity = activity as MainActivity
        val countryDao = mainActivity.db?.countryDao()

        // retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
        val countryService = retrofit.create(CountryService::class.java)
        val countryRepository = CountriesRepository(countryService)
        if (mainActivity.isNetworkConnected()) {
            countryRepository.getCountries()?.enqueue(object : Callback<List<Country>> {
                override fun onResponse(
                    call: Call<List<Country>>,
                    response: Response<List<Country>>
                ) {

                    val countries = response.body() ?: return

                    GlobalScope.launch {
                        // get saved entities
                        val allContries = countryDao?.getAllCountries();
                        // iteration of received countries and save or update in database
                        for (country in countries) {
                            val currentCountryEntity = CountryEntity(
                                name = country.name,
                                capital = country.capital,
                                flagUrl = country.flags.png,
                                region = null,
                                population = null,
                                area = null
                            )
                            if (allContries?.find { it.name == country.name } == null) {
                                countryDao?.insertCountry(currentCountryEntity)
                            } else {
                                countryDao?.updateCountry(currentCountryEntity)
                            }
                        }
                    }

                    // call adapter with countries
                    val adapter = CountryAdapter(countries)
                    binding.countriesList.adapter = adapter
                }

                override fun onFailure(call: Call<List<Country>>, t: Throwable) {

                    Snackbar.make(binding.root, "Failed to fetch countries", Snackbar.LENGTH_LONG)
                        .show()
                }
            })
        } else {
            var countries: List<Country>? = null

            GlobalScope.launch {
                val savedCountries = countryDao?.getAllCountries()
                countries = savedCountries?.map { it ->
                    Country(
                        name = it.name ?: "",
                        capital = it.capital ?: "",
                        flags = Flags(png = it.flagUrl ?: "", svg = "")
                    )
                }?.toList()

            }

            //workaround for wait get data from room
            while (countries?.size == null) {

            }

            val adapter = CountryAdapter(countries ?: listOf<Country>())
            binding.countriesList.adapter = adapter

        }
        return binding.root
    }


}