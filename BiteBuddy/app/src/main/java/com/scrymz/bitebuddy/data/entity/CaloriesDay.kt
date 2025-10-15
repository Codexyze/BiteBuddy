package com.scrymz.bitebuddy.data.entity

import androidx.annotation.Keep

@Keep
data class CaloriesDay(
    val dateConsumed: String,
    val totalCalories: Double
)
