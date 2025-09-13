package com.scrymz.bitebuddy.data.repoImpl

import com.scrymz.bitebuddy.data.dao.FoodTableDao
import com.scrymz.bitebuddy.data.entity.FoodTable
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.repository.FoodDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class FoodDatabaseRepositoryImpl @Inject constructor(
    private val dao: FoodTableDao
) : FoodDatabaseRepository {

    override suspend fun upsertFood(food: FoodTable): Flow<ResultState<String>> = flow {
        emit(ResultState.loading)
        try {
            dao.upsertFoodTable(food)
            emit(ResultState.Sucess("Food inserted/updated successfully"))
        } catch (e: Exception) {
            emit(ResultState.Error("Failed to upsert: ${e.message}"))
        }
    }

    override suspend fun deleteFood(food: FoodTable): Flow<ResultState<String>> = flow {
        emit(ResultState.loading)
        try {
            dao.deleteFoodTable(food)
            emit(ResultState.Sucess("Food deleted successfully"))
        } catch (e: Exception) {
            emit(ResultState.Error("Failed to delete: ${e.message}"))
        }
    }

    override fun getAllSortedByDate(): Flow<ResultState<List<FoodTable>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getAllSortedByDate().collect { list ->
                emit(ResultState.Sucess(list))
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Failed to fetch data: ${e.message}"))
        }
    }

    override fun getByDate(date: String): Flow<ResultState<List<FoodTable>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getByDate(date).collect { list ->
                emit(ResultState.Sucess(list))
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Failed to fetch data: ${e.message}"))
        }
    }

    override fun getByConsumedTime(time: String): Flow<ResultState<List<FoodTable>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getByConsumedTime(time).collect { list ->
                emit(ResultState.Sucess(list))
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Failed to fetch data: ${e.message}"))
        }
    }

    override fun getOnlyProtein(date: String): Flow<ResultState<Double>> = flow {
        emit(ResultState.loading)
        try {
            dao.getOnlyProtein(date).collect { value ->
                emit(ResultState.Sucess(value))
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Failed to get protein: ${e.message}"))
        }
    }

    override fun getOnlyCalories(date: String): Flow<ResultState<Double>> = flow {
        emit(ResultState.loading)
        try {
            dao.getOnlyCalories(date).collect { value ->
                emit(ResultState.Sucess(value))
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Failed to get calories: ${e.message}"))
        }
    }
}
