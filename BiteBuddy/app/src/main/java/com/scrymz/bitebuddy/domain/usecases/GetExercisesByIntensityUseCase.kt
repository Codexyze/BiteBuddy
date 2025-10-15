package com.scrymz.bitebuddy.domain.usecases


import com.scrymz.bitebuddy.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetExercisesByIntensityUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(intensity: String) = repository.getExercisesByIntensity(intensity)
}