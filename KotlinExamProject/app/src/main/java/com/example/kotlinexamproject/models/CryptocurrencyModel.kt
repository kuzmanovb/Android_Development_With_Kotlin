package com.example.kotlinexamproject.models

import android.icu.text.SymbolTable

data class CryptocurrencyModel(
    var id: String,
    var image: String,
    var name: String,
    var symbol: String,
    var current_price: String,
    var market_cap: String,
    var high_24h: String,
    var low_24h: String,
    var price_change_percentage_24h: String,
    var market_cap_change_percentage_24h: String
)
