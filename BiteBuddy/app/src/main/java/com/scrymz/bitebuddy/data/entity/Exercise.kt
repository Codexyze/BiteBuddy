package com.scrymz.bitebuddy.data.entity
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scrymz.bitebuddy.Constants.Constants
@Keep
@Entity(tableName = Constants.EXERCISE_TABLE)
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val exerciseName: String, // e.g., "Running", "Bench Press", "Yoga"
    val exerciseType: String = Constants.EXERCISE_CARDIO, // Cardio, Strength, Flexibility, Sports, Yoga, HIIT
    val durationMinutes: Int, // Duration in minutes
    val caloriesBurned: Double? = null, // Estimated calories burned
    val intensity: String = Constants.INTENSITY_MEDIUM, // Low, Medium, High, Very High
    val sets: Int? = null, // For strength training
    val reps: Int? = null, // For strength training
    val weight: Double? = null, // Weight used in kg (for strength training)
    val distance: Double? = null, // Distance in km (for cardio)
    val date: String, // "yyyy-MM-dd"
    val timeOfDay: String = Constants.MORNING, // Morning, Afternoon, Evening, Night
    val month: Int, // 1-12
    val year: Int,
    val notes: String? = null,
    val heartRate: Int? = null, // Average heart rate during exercise
    val isCompleted: Boolean = true // Track if workout was completed
)
