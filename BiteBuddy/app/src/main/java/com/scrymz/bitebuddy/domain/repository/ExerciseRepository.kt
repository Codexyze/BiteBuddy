package com.scrymz.bitebuddy.domain.repository
import com.scrymz.bitebuddy.data.entity.Exercise
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun upsertExercise(exercise: Exercise): Flow<ResultState<String>>
    fun deleteExercise(exercise: Exercise): Flow<ResultState<String>>
    fun getAllExercisesDescending(): Flow<ResultState<List<Exercise>>>
    fun getExercisesByDate(date: String): Flow<ResultState<List<Exercise>>>
    fun getExercisesByMonth(month: Int, year: Int): Flow<ResultState<List<Exercise>>>
    fun getExercisesByType(type: String): Flow<ResultState<List<Exercise>>>
    fun getExercisesByIntensity(intensity: String): Flow<ResultState<List<Exercise>>>
    fun getExerciseById(id: Long): Flow<ResultState<Exercise?>>
    fun getExercisesByYear(year: Int): Flow<ResultState<List<Exercise>>>
    fun getTotalCaloriesBurnedByDate(date: String): Flow<ResultState<Double>>
    fun getTotalDurationByDate(date: String): Flow<ResultState<Int>>
    fun getTotalCaloriesBurnedByMonth(month: Int, year: Int): Flow<ResultState<Double>>
    fun getTotalDurationByMonth(month: Int, year: Int): Flow<ResultState<Int>>
}
