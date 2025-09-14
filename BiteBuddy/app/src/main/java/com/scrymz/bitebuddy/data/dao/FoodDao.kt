package com.scrymz.bitebuddy.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.scrymz.bitebuddy.data.local.model.model.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_table")
    fun getAllFood(): Flow<List<Food>>

    @Query("SELECT * FROM food_table WHERE foodname LIKE '%' || :query || '%'")
    fun searchFoodByName(query: String): Flow<List<Food>>

    @Query("SELECT * FROM food_table WHERE type = :type")
    fun getFoodByType(type: String): Flow<List<Food>>

    @Query("SELECT * FROM food_table WHERE safeInPregnancy = 1")
    fun getPregnancySafeFood(): Flow<List<Food>>

    @Query("SELECT * FROM food_table WHERE menstrualSafe = 1")
    fun getMenstrualSafeFood(): Flow<List<Food>>

    @Query("SELECT * FROM food_table WHERE femaleImportant = 1")
    fun getFemaleImportantFood(): Flow<List<Food>>

    @Query("SELECT * FROM food_table WHERE maleImportant = 1")
    fun getMaleImportantFood(): Flow<List<Food>>
}