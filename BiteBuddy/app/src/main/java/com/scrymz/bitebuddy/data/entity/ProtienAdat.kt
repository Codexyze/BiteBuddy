package com.scrymz.bitebuddy.data.entity

import androidx.annotation.Keep

@Keep
data class ProteinDay(
    val dateConsumed: String,
    val totalProtein: Double
)

