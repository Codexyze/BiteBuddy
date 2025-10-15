package com.scrymz.bitebuddy.domain.usecases


import com.scrymz.bitebuddy.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetTotalCaloriesBurnedByDateUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(date: String) = repository.getTotalCaloriesBurnedByDate(date)
}