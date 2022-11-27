package com.example.kotlinexamproject.viewModels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import com.example.kotlinexamproject.api.ApiRepository
import com.example.kotlinexamproject.db.AppDatabase
import com.example.kotlinexamproject.db.entities.CryptocurrencyEntity
import com.example.kotlinexamproject.models.CryptocurrencyModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CryptocurrencyViewModel(
    private var apiRepository: ApiRepository,
    private var db: AppDatabase
) : ViewModel() {

    private var _cryptocurrencies = MutableStateFlow<List<CryptocurrencyEntity>>(arrayListOf())
    var cryptocurrencies: StateFlow<List<CryptocurrencyEntity>> = _cryptocurrencies.asStateFlow()

    private var _currentCryptocurrency = MutableStateFlow<CryptocurrencyEntity?>(null)
    var currentCryptocurrency: StateFlow<CryptocurrencyEntity?> =
        _currentCryptocurrency.asStateFlow()

    private var _changeIsFavourite = MutableStateFlow<Boolean>(false)
    var changeIsFavourite: StateFlow<Boolean> = _changeIsFavourite.asStateFlow()

    fun getCryptocurrencies() {

        apiRepository.getCryptocurrencies()?.enqueue(object : Callback<List<CryptocurrencyModel>> {
            override fun onResponse(
                call: Call<List<CryptocurrencyModel>>,
                response: Response<List<CryptocurrencyModel>>
            ) {

                val dataRespons = response.body() ?: return

                GlobalScope.launch {
                    // get saved entities
                    val allCryptocurrencies = db.cryptocurrencyDao().getAllCryptocurrencies()
                    // iteration of received cryptocurrency and save or update in database
                    for (cryptocurrency in dataRespons) {
                        val cryptocurrencyEntity = CryptocurrencyEntity(
                            id = cryptocurrency.id,
                            name = cryptocurrency.name,
                            image = cryptocurrency.image,
                            symbol = cryptocurrency.symbol,
                            current_price = cryptocurrency.current_price,
                            market_cap = cryptocurrency.market_cap,
                            high_24h = cryptocurrency.high_24h,
                            low_24h = cryptocurrency.low_24h,
                            price_change_percentage_24h = cryptocurrency.price_change_percentage_24h,
                            market_cap_change_percentage_24h = cryptocurrency.market_cap_change_percentage_24h,
                        )
                        if (allCryptocurrencies.find { it.id == cryptocurrency.id } == null) {
                            db.cryptocurrencyDao().insertCryptocurrency(cryptocurrencyEntity)
                        } else {
                            val isFavourite = allCryptocurrencies.find { it.id == cryptocurrency.id }!!.isFavourite
                            cryptocurrencyEntity.isFavourite = isFavourite
                            db.cryptocurrencyDao().updateCryptocurrency(cryptocurrencyEntity)
                        }
                    }

                    _cryptocurrencies.value = db.cryptocurrencyDao().getAllCryptocurrencies()
                }

            }

            override fun onFailure(call: Call<List<CryptocurrencyModel>>, t: Throwable) {

            }

        })

        GlobalScope.launch {
            _cryptocurrencies.value = db.cryptocurrencyDao().getAllCryptocurrencies()
        }
    }

    fun getCryptocurrencyByName(name: String) {

        GlobalScope.launch {
            val currentCryptocurrency = db.cryptocurrencyDao().getCryptocurrencyByName(name)
            _currentCryptocurrency.value = currentCryptocurrency
            _changeIsFavourite.value = currentCryptocurrency.isFavourite

        }
    }

    fun setCryptocurrencyAsFavourite(name: String){

        GlobalScope.launch {
            // get saved entities
            val currentCryptocurrency = db.cryptocurrencyDao().getCryptocurrencyByName(name)
            currentCryptocurrency.isFavourite = !currentCryptocurrency.isFavourite
            db.cryptocurrencyDao().updateCryptocurrency(currentCryptocurrency)

            _changeIsFavourite.value = !_changeIsFavourite.value
        }

    }

}
