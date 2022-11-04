package net.gostartups.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import net.gostartups.myapplication.databinding.FragmentCountryDescriptionBinding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountryDescriptionFragment : Fragment() {
    private lateinit var binding: FragmentCountryDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryDescriptionBinding.inflate(inflater, container, false)

        val countryName = arguments?.getString("countryName").toString()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        val countryService = retrofit.create(CountryService::class.java)
        val countryRepository = CountriesRepository(countryService)

        countryRepository.getCountry(countryName)
            ?.enqueue(object : Callback<List<CountryDescription>> {
                override fun onResponse(
                    call: Call<List<CountryDescription>>,
                    response: Response<List<CountryDescription>>
                ) {
                    val country = response.body() ?: return
                    val currentCountry = country[0]
                    binding.countryName.text = "Name : ${currentCountry.name}"
                    binding.countryCapital.text = "Capital : ${currentCountry.capital}"
                    binding.countryArea.text = "Area : ${currentCountry.area.toString()}"
                    binding.countryRegion.text ="Region : ${currentCountry.region}"
                    binding.countryPopulation.text = "Population : ${currentCountry.population.toString()}"

                    Glide
                        .with(binding.root)
                        .load(currentCountry.flags.png)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(binding.flag)
                }

                override fun onFailure(call: Call<List<CountryDescription>>, t: Throwable) {
                    Snackbar.make(binding.root, "Failed to fetch countries", Snackbar.LENGTH_LONG)
                        .show()
                }
            })

        binding.buttonBack.setOnClickListener {
            it.findNavController().navigate(R.id.action_countryDescription_to_Countries)

        }

        return binding.root
    }
}