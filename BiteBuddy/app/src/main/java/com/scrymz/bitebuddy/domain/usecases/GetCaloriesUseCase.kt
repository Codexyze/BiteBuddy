package com.scrymz.bitebuddy.domain.usecases


import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.repository.FoodDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class GetCaloriesUseCase @Inject constructor(
    private val repository: FoodDatabaseRepository
) {
    operator fun invoke(date: String): Flow<ResultState<Double>> {
        return repository.getOnlyCalories(date)
    }
}