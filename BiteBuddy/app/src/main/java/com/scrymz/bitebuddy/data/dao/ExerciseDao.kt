package com.scrymz.bitebuddy.data.dao


import androidx.annotation.Keep
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.scrymz.bitebuddy.Constants.Constants
import com.scrymz.bitebuddy.data.entity.Exercise
import kotlinx.coroutines.flow.Flow
@Keep
@Dao
interface ExerciseDao {
    @Insert
    suspend fun insertExercise(exercise: Exercise)

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("SELECT * FROM ${Constants.EXERCISE_TABLE} ORDER BY year DESC, month DESC, date DESC")
    fun getAllExercisesDescending(): Flow<List<Exercise>>

    @Query("SELECT * FROM ${Constants.EXERCISE_TABLE} WHERE date = :date ORDER BY timeOfDay")
    fun getExercisesByDate(date: String): Flow<List<Exercise>>

    @Query("SELECT * FROM ${Constants.EXERCISE_TABLE} WHERE month = :month AND year = :year ORDER BY date DESC")
    fun getExercisesByMonth(month: Int, year: Int): Flow<List<Exercise>>

    @Query("SELECT * FROM ${Constants.EXERCISE_TABLE} WHERE exerciseType = :type ORDER BY year DESC, month DESC, date DESC")
    fun getExercisesByType(type: String): Flow<List<Exercise>>

    @Query("SELECT * FROM ${Constants.EXERCISE_TABLE} WHERE intensity = :intensity ORDER BY year DESC, month DESC, date DESC")
    fun getExercisesByIntensity(intensity: String): Flow<List<Exercise>>

    @Query("SELECT * FROM ${Constants.EXERCISE_TABLE} WHERE id = :id")
    suspend fun getExerciseById(id: Long): Exercise?

    @Query("SELECT * FROM ${Constants.EXERCISE_TABLE} WHERE year = :year ORDER BY month DESC, date DESC")
    fun getExercisesByYear(year: Int): Flow<List<Exercise>>

    @Query("SELECT SUM(caloriesBurned) FROM ${Constants.EXERCISE_TABLE} WHERE date = :date")
    suspend fun getTotalCaloriesBurnedByDate(date: String): Double?

    @Query("SELECT SUM(durationMinutes) FROM ${Constants.EXERCISE_TABLE} WHERE date = :date")
    suspend fun getTotalDurationByDate(date: String): Int?

    @Query("SELECT SUM(caloriesBurned) FROM ${Constants.EXERCISE_TABLE} WHERE month = :month AND year = :year")
    suspend fun getTotalCaloriesBurnedByMonth(month: Int, year: Int): Double?

    @Query("SELECT SUM(durationMinutes) FROM ${Constants.EXERCISE_TABLE} WHERE month = :month AND year = :year")
    suspend fun getTotalDurationByMonth(month: Int, year: Int): Int?
}