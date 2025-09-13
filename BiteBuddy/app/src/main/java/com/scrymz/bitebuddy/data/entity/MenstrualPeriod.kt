package com.scrymz.bitebuddy.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scrymz.bitebuddy.Constants.Constants


@Entity(tableName = Constants.MENSTRUAL_TABLE)
data class MenstrualPeriod(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val startDate: String, // Store as "yyyy-MM-dd"
    val endDate: String? = null,
    val painLevel: String = Constants.PAIN_NORMAL, // Easy, Mild, Normal, Moderate, Severe
    val timeAppeared: String = Constants.MORNING, // Morning, Afternoon, Evening, Night
    val flowIntensity: String = Constants.FLOW_NORMAL,
    val month: Int, // 1-12
    val year: Int,
    val symptoms: String? = null,
    val notes: String? = null
)