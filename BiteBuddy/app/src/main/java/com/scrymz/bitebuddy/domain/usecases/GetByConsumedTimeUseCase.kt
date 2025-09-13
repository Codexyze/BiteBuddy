package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.data.entity.FoodTable
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.repository.FoodDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class GetByConsumedTimeUseCase @Inject constructor(
    private val repository: FoodDatabaseRepository
) {
    operator fun invoke(time: String): Flow<ResultState<List<FoodTable>>> {
        return repository.getByConsumedTime(time)
    }
}