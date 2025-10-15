package com.scrymz.bitebuddy.domain.usecases


import com.scrymz.bitebuddy.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetTotalCaloriesBurnedByMonthUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(month: Int, year: Int) = repository.getTotalCaloriesBurnedByMonth(month, year)
}