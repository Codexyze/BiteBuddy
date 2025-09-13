package com.scrymz.bitebuddy.domain.repository

import com.scrymz.bitebuddy.data.entity.FoodTable
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import kotlinx.coroutines.flow.Flow


interface FoodDatabaseRepository {
    suspend fun upsertFood(food: FoodTable): Flow<ResultState<String>>
    suspend fun deleteFood(food: FoodTable): Flow<ResultState<String>>
    fun getAllSortedByDate(): Flow<ResultState<List<FoodTable>>>
    fun getByDate(date: String): Flow<ResultState<List<FoodTable>>>
    fun getByConsumedTime(time: String): Flow<ResultState<List<FoodTable>>>
    fun getOnlyProtein(date: String): Flow<ResultState<Double>>
    fun getOnlyCalories(date: String): Flow<ResultState<Double>>
}
