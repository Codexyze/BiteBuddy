package com.scrymz.bitebuddy.domain.usecases


import com.scrymz.bitebuddy.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetExercisesByTypeUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(type: String) = repository.getExercisesByType(type)
}
