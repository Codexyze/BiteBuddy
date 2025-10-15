package com.scrymz.bitebuddy.domain.usecases


import com.scrymz.bitebuddy.data.entity.Exercise
import com.scrymz.bitebuddy.domain.repository.ExerciseRepository
import javax.inject.Inject

class DeleteExerciseUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(exercise: Exercise) = repository.deleteExercise(exercise)
}
