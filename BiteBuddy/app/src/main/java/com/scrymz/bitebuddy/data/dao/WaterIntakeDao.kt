package com.scrymz.bitebuddy.data.dao

import androidx.annotation.Keep
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.scrymz.bitebuddy.Constants.Constants
import com.scrymz.bitebuddy.data.entity.WaterIntake
import kotlinx.coroutines.flow.Flow
@Keep
@Dao
interface WaterIntakeDao {
    @Insert
    suspend fun insertWaterIntake(waterIntake: WaterIntake)

    @Update
    suspend fun updateWaterIntake(waterIntake: WaterIntake)

    @Delete
    suspend fun deleteWaterIntake(waterIntake: WaterIntake)

    @Query("SELECT * FROM ${Constants.WATER_INTAKE_TABLE} ORDER BY year DESC, month DESC, date DESC, time DESC")
    fun getAllWaterIntakesDescending(): Flow<List<WaterIntake>>

    @Query("SELECT * FROM ${Constants.WATER_INTAKE_TABLE} WHERE date = :date ORDER BY time DESC")
    fun getWaterIntakesByDate(date: String): Flow<List<WaterIntake>>

    @Query("SELECT * FROM ${Constants.WATER_INTAKE_TABLE} WHERE month = :month AND year = :year ORDER BY date DESC, time DESC")
    fun getWaterIntakesByMonth(month: Int, year: Int): Flow<List<WaterIntake>>

    @Query("SELECT * FROM ${Constants.WATER_INTAKE_TABLE} WHERE id = :id")
    suspend fun getWaterIntakeById(id: Long): WaterIntake?

    @Query("SELECT * FROM ${Constants.WATER_INTAKE_TABLE} WHERE year = :year ORDER BY month DESC, date DESC")
    fun getWaterIntakesByYear(year: Int): Flow<List<WaterIntake>>

    @Query("SELECT SUM(amountMl) FROM ${Constants.WATER_INTAKE_TABLE} WHERE date = :date")
    suspend fun getTotalWaterByDate(date: String): Double?

    @Query("SELECT SUM(amountMl) FROM ${Constants.WATER_INTAKE_TABLE} WHERE month = :month AND year = :year")
    suspend fun getTotalWaterByMonth(month: Int, year: Int): Double?

    @Query("SELECT SUM(amountMl) FROM ${Constants.WATER_INTAKE_TABLE} WHERE year = :year")
    suspend fun getTotalWaterByYear(year: Int): Double?

    @Query("SELECT AVG(amountMl) FROM ${Constants.WATER_INTAKE_TABLE} WHERE month = :month AND year = :year")
    suspend fun getAverageWaterByMonth(month: Int, year: Int): Double?

    @Query("DELETE FROM ${Constants.WATER_INTAKE_TABLE} WHERE date = :date")
    suspend fun deleteWaterIntakesByDate(date: String)
}
