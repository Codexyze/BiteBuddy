package com.scrymz.bitebuddy.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.scrymz.bitebuddy.Constants.Constants
import com.scrymz.bitebuddy.data.entity.CaloriesDay
import com.scrymz.bitebuddy.data.entity.FoodTable
import com.scrymz.bitebuddy.data.entity.ProteinDay
import kotlinx.coroutines.flow.Flow


@Dao
interface FoodTableDao {

    // ✅ Insert or update
    @Upsert
    suspend fun upsertFoodTable(foodTable: FoodTable)

    // ✅ Delete a record
    @Delete
    suspend fun deleteFoodTable(foodTable: FoodTable)

    // ✅ Get all records sorted by date (latest first)
    @Query("SELECT * FROM ${Constants.FOODTABLETRACK} ORDER BY dateConsumed DESC, timeConsumed ASC")
    fun getAllSortedByDate(): Flow<List<FoodTable>>

    // ✅ Get records for a specific date
    @Query("SELECT * FROM ${Constants.FOODTABLETRACK} WHERE dateConsumed = :date ORDER BY timeConsumed ASC")
    fun getByDate(date: String): Flow<List<FoodTable>>

    // ✅ Get records for a specific time (Morning, Snack, Evening etc.)
    @Query("SELECT * FROM ${Constants.FOODTABLETRACK} WHERE timeConsumed = :time ORDER BY dateConsumed DESC")
    fun getByConsumedTime(time: String): Flow<List<FoodTable>>

    // ✅ Get total protein for a specific date
    @Query("SELECT IFNULL(SUM(protein), 0.0) FROM ${Constants.FOODTABLETRACK} WHERE dateConsumed = :date")
    fun getOnlyProtein(date: String): Flow<Double>

    // ✅ Get total calories for a specific date
    @Query("SELECT IFNULL(SUM(calories), 0.0) FROM ${Constants.FOODTABLETRACK} WHERE dateConsumed = :date")
    fun getOnlyCalories(date: String): Flow<Double>

    // ✅ Get protein grouped by date (for charts)
    @Query("SELECT dateConsumed, SUM(protein) as totalProtein FROM ${Constants.FOODTABLETRACK} GROUP BY dateConsumed ORDER BY dateConsumed ASC")
    fun getProteinOverDays(): Flow<List<ProteinDay>>

    // ✅ Get calories grouped by date (for charts)
    @Query("SELECT dateConsumed, SUM(calories) as totalCalories FROM ${Constants.FOODTABLETRACK} GROUP BY dateConsumed ORDER BY dateConsumed ASC")
    fun getCaloriesOverDays(): Flow<List<CaloriesDay>>
}
