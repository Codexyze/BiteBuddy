package com.scrymz.bitebuddy.domain.usecases


import com.scrymz.bitebuddy.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetExercisesByDateUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(date: String) = repository.getExercisesByDate(date)
}
