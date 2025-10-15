package com.scrymz.bitebuddy.data.entity


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scrymz.bitebuddy.Constants.Constants
@Keep
@Entity(tableName = Constants.WATER_INTAKE_TABLE)
data class WaterIntake(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amountMl: Double, // Amount in milliliters
    val date: String, // "yyyy-MM-dd"
    val time: String, // "HH:mm" format
    val timeOfDay: String = Constants.MORNING, // Morning, Afternoon, Evening, Night
    val containerType: String? = null, // "Glass", "Bottle", "Cup"
    val month: Int, // 1-12
    val year: Int,
    val notes: String? = null,
    val temperature: String? = null // "Cold", "Room Temp", "Warm"
)
