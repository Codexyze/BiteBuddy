package com.scrymz.bitebuddy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scrymz.bitebuddy.data.dao.FoodTableDao
import com.scrymz.bitebuddy.data.dao.MenstrualDao
import com.scrymz.bitebuddy.data.entity.FoodTable
import com.scrymz.bitebuddy.data.entity.MenstrualPeriod

@Database(entities = [FoodTable::class, MenstrualPeriod::class], version = 3, exportSchema = false)
abstract class FoodDatabase : RoomDatabase(){
    abstract fun foodTableDao(): FoodTableDao
    abstract fun menstrualPeriodDao(): MenstrualDao
}