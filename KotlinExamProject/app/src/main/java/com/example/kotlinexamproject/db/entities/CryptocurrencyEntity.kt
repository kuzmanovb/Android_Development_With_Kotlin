package com.example.kotlinexamproject.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CryptocurrencyEntity(
    @PrimaryKey var id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "symbol") var symbol: String,
    @ColumnInfo(name = "current_price") var current_price: String,
    @ColumnInfo(name = "market_cap") var market_cap: String,
    @ColumnInfo(name = "high_24h") var high_24h: String,
    @ColumnInfo(name = "low_24h") var low_24h: String,
    @ColumnInfo(name = "price_change_percentage_24h") var price_change_percentage_24h: String,
    @ColumnInfo(name = "market_cap_change_percentage_24h") var market_cap_change_percentage_24h: String,
    @ColumnInfo(name="is_favourite") var isFavourite : Boolean = false
)
