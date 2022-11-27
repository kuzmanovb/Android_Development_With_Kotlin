package com.example.kotlinexamproject.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlinexamproject.db.daos.CryptocurrencyDao
import com.example.kotlinexamproject.db.entities.CryptocurrencyEntity

@Database(entities = [CryptocurrencyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cryptocurrencyDao(): CryptocurrencyDao
}