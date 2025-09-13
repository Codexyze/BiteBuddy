package com.scrymz.bitebuddy.di.modules

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.scrymz.bitebuddy.core.DataBaseOpenHelper
import com.scrymz.bitebuddy.data.database.FoodDatabase
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
        ).build()
    }





}