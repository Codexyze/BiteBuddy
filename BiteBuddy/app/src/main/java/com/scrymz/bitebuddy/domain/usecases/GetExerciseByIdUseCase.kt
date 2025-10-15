package com.scrymz.bitebuddy.domain.usecases



import com.scrymz.bitebuddy.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetExerciseByIdUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(id: Long) = repository.getExerciseById(id)
}