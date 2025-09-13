package com.scrymz.bitebuddy.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.scrymz.bitebuddy.Constants.Constants
import com.scrymz.bitebuddy.data.entity.MenstrualPeriod
import kotlinx.coroutines.flow.Flow


@Dao
interface MenstrualDao {
    @Insert
    suspend fun insertPeriod(period: MenstrualPeriod)

    @Update
    suspend fun updatePeriod(period: MenstrualPeriod)

    @Delete
    suspend fun deletePeriod(period: MenstrualPeriod)

    @Query("SELECT * FROM ${Constants.MENSTRUAL_TABLE} ORDER BY year DESC, month DESC, startDate DESC")
    fun getAllPeriodsDescending(): Flow<List<MenstrualPeriod>>

    @Query("SELECT * FROM ${Constants.MENSTRUAL_TABLE} WHERE month = :month AND year = :year ORDER BY startDate DESC")
    fun getPeriodsByMonth(month: Int, year: Int): Flow<List<MenstrualPeriod>>

    @Query("SELECT * FROM ${Constants.MENSTRUAL_TABLE} WHERE painLevel = :painLevel ORDER BY year DESC, month DESC, startDate DESC")
    fun getPeriodsByPainLevel(painLevel: String): Flow<List<MenstrualPeriod>>

    @Query("SELECT * FROM ${Constants.MENSTRUAL_TABLE} WHERE timeAppeared = :timeOfDay ORDER BY year DESC, month DESC, startDate DESC")
    fun getPeriodsByTimeOfDay(timeOfDay: String): Flow<List<MenstrualPeriod>>

    @Query("SELECT * FROM ${Constants.MENSTRUAL_TABLE} WHERE id = :id")
    suspend fun getPeriodById(id: Long): MenstrualPeriod?

    @Query("SELECT * FROM ${Constants.MENSTRUAL_TABLE} WHERE year = :year ORDER BY month DESC, startDate DESC")
    fun getPeriodsByYear(year: Int): Flow<List<MenstrualPeriod>>
}