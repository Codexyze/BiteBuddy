package com.scrymz.bitebuddy.domain.repository


import com.scrymz.bitebuddy.data.entity.WaterIntake
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import kotlinx.coroutines.flow.Flow

interface WaterIntakeRepository {
    fun upsertWaterIntake(waterIntake: WaterIntake): Flow<ResultState<String>>
    fun deleteWaterIntake(waterIntake: WaterIntake): Flow<ResultState<String>>
    fun getAllWaterIntakesDescending(): Flow<ResultState<List<WaterIntake>>>
    fun getWaterIntakesByDate(date: String): Flow<ResultState<List<WaterIntake>>>
    fun getWaterIntakesByMonth(month: Int, year: Int): Flow<ResultState<List<WaterIntake>>>
    fun getWaterIntakeById(id: Long): Flow<ResultState<WaterIntake?>>
    fun getWaterIntakesByYear(year: Int): Flow<ResultState<List<WaterIntake>>>
    fun getTotalWaterByDate(date: String): Flow<ResultState<Double>>
    fun getTotalWaterByMonth(month: Int, year: Int): Flow<ResultState<Double>>
    fun getTotalWaterByYear(year: Int): Flow<ResultState<Double>>
    fun getAverageWaterByMonth(month: Int, year: Int): Flow<ResultState<Double>>
    fun deleteWaterIntakesByDate(date: String): Flow<ResultState<String>>
}
