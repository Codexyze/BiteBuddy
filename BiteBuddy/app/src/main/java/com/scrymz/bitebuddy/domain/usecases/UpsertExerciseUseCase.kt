package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.data.entity.Exercise
import com.scrymz.bitebuddy.domain.repository.ExerciseRepository
import javax.inject.Inject

class UpsertExerciseUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(exercise: Exercise) = repository.upsertExercise(exercise)
}
