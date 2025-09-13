package com.scrymz.bitebuddy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scrymz.bitebuddy.data.dao.FoodTableDao
import com.scrymz.bitebuddy.data.entity.FoodTable

@Database(entities = [FoodTable::class], version = 1, exportSchema = false)
abstract class FoodDatabase : RoomDatabase(){
    abstract fun foodTableDao(): FoodTableDao
}