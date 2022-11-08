package net.gostartups.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "capital") var capital: String?,
    @ColumnInfo(name = "flag_url") var flagUrl: String?,
    @ColumnInfo(name = "region") var region: String?,
    @ColumnInfo(name = "population") var population: Int?,
    @ColumnInfo(name = "area") var area: Int?,
)
