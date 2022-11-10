package net.gostartups.myapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CountryDao {

    @Query("SELECT * FROM CountryEntity")
    suspend fun getAllCountries() : List<CountryEntity>

    @Query("SELECT * FROM CountryEntity WHERE name = :name")
    suspend fun getCountryByName(name: String) : CountryEntity

    @Insert
    fun insertCountry(vararg country: CountryEntity)

    @Update
    fun updateCountry(vararg country: CountryEntity)

    @Delete
    fun deleteCountry(country: CountryEntity)
}