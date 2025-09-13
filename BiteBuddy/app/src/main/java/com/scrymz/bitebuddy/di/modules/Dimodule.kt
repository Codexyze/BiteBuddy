package com.scrymz.bitebuddy.di.modules

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.scrymz.bitebuddy.core.DataBaseOpenHelper
import com.scrymz.bitebuddy.data.dao.FoodTableDao
import com.scrymz.bitebuddy.data.dao.MenstrualDao
import com.scrymz.bitebuddy.data.database.FoodDatabase
import com.scrymz.bitebuddy.data.repoImpl.FoodDatabaseRepositoryImpl
import com.scrymz.bitebuddy.domain.repository.FoodDatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Dimodule {
    @Provides
    @Singleton
    fun databaseOpner(@ApplicationContext context: Context): DataBaseOpenHelper{
        return DataBaseOpenHelper(context = context)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RoomDatabase{
        return Room.databaseBuilder(
          context = context,
            FoodDatabase::class.java, "food_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideFoodDao(@ApplicationContext context: Context): FoodTableDao{
         return Room.databaseBuilder(
            context = context,
            FoodDatabase::class.java, "food_db"
        ).build().foodTableDao()
    }



    @Singleton
    @Provides
    fun provideMenstrualDatabase(@ApplicationContext context: Context): MenstrualDao {
        return Room.databaseBuilder(
            context = context,
            FoodDatabase::class.java, "food_db"
        ).build().menstrualPeriodDao()
    }




}