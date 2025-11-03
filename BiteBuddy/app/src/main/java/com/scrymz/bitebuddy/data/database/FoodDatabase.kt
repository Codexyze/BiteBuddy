package com.scrymz.bitebuddy.data.database

import androidx.annotation.Keep
import androidx.room.Database
import androidx.room.RoomDatabase
import com.scrymz.bitebuddy.data.dao.ExerciseDao
import com.scrymz.bitebuddy.data.dao.FoodTableDao
import com.scrymz.bitebuddy.data.dao.ImageToProgressDao
import com.scrymz.bitebuddy.data.dao.MenstrualDao
import com.scrymz.bitebuddy.data.dao.WaterIntakeDao
import com.scrymz.bitebuddy.data.entity.Exercise
import com.scrymz.bitebuddy.data.entity.FoodTable
import com.scrymz.bitebuddy.data.entity.ImageToProgress
import com.scrymz.bitebuddy.data.entity.MenstrualPeriod
import com.scrymz.bitebuddy.data.entity.WaterIntake
@Keep
@Database(entities = [FoodTable::class,
    MenstrualPeriod::class,
    WaterIntake::class,
    Exercise::class,
    ImageToProgress::class

                     ], version = 6 , exportSchema = false)
abstract class FoodDatabase : RoomDatabase(){
    abstract fun foodTableDao(): FoodTableDao
    abstract fun menstrualPeriodDao(): MenstrualDao

    abstract fun waterInkateDao(): WaterIntakeDao

    abstract fun exerciseDao(): ExerciseDao

    abstract fun imageToProgressDao(): ImageToProgressDao
}