package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.data.entity.FoodTable
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.repository.FoodDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class UpsertFoodUseCase @Inject constructor(
    private val repository: FoodDatabaseRepository
) {
    suspend operator fun invoke(food: FoodTable): Flow<ResultState<String>> {
        return repository.upsertFood(food)
    }
}
