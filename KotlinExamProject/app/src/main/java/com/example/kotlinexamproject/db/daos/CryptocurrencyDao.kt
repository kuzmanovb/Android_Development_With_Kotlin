package com.example.kotlinexamproject.db.daos

import androidx.room.*
import com.example.kotlinexamproject.db.entities.CryptocurrencyEntity

@Dao
interface CryptocurrencyDao {

    @Query("SELECT * FROM CryptocurrencyEntity")
    suspend fun getAllCryptocurrencies() : List<CryptocurrencyEntity>

    @Query("SELECT * FROM CryptocurrencyEntity WHERE name = :name")
    suspend fun getCryptocurrencyByName(name: String) : CryptocurrencyEntity

    @Insert
    fun insertCryptocurrency(vararg cryptocurrency: CryptocurrencyEntity)

    @Update
    fun updateCryptocurrency(vararg cryptocurrency: CryptocurrencyEntity)

    @Delete
    fun deleteCryptocurrency(cryptocurrency: CryptocurrencyEntity)


}