package com.scrymz.bitebuddy.domain.repository

import com.scrymz.bitebuddy.data.entity.MenstrualPeriod
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import kotlinx.coroutines.flow.Flow


interface MenstrualRepository {
    fun upsertPeriod(period: MenstrualPeriod): Flow<ResultState<String>>
    fun deletePeriod(period: MenstrualPeriod): Flow<ResultState<String>>
    fun getAllPeriodsDescending(): Flow<ResultState<List<MenstrualPeriod>>>
    fun getPeriodsByMonth(month: Int, year: Int): Flow<ResultState<List<MenstrualPeriod>>>
    fun getPeriodsByPainLevel(painLevel: String): Flow<ResultState<List<MenstrualPeriod>>>
    fun getPeriodsByTimeOfDay(timeOfDay: String): Flow<ResultState<List<MenstrualPeriod>>>
    fun getPeriodById(id: Long): Flow<ResultState<MenstrualPeriod?>>
    fun getPeriodsByYear(year: Int): Flow<ResultState<List<MenstrualPeriod>>>
}