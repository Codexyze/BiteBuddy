package com.scrymz.bitebuddy.presentation.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
object HOMESCREEN

@Serializable
data class FOODINFOSCREEN(
    val id: Int = 0,                        // INTEGER PRIMARY KEY AUTOINCREMENT
    val type: String? = null,               // TEXT (nullable)
    val foodname: String,                   // TEXT UNIQUE (required)
    val pergram: Double? = null,            // REAL (nullable)
    val calories: Double? = null,           // REAL (nullable)
    val protein: Double? = null,            // REAL (nullable)
    val calcium: Double? = null,            // REAL (nullable)
    val iron: Double? = null,               // REAL (nullable)
    val magnesium: Double? = null,          // REAL (nullable)
    val vitA: Double? = null,               // REAL (vit_a)
    val vitB12: Double? = null,             // REAL (vit_b12)
    val vitC: Double? = null,               // REAL (vit_c)
    val vitD: Double? = null,               // REAL (vit_d)
    val safeInPregnancy: Boolean = false,   // INTEGER (0/1 -> Boolean)
    val menstrualSafe: Boolean = false,     // INTEGER (0/1 -> Boolean)
    val femaleImportant: Boolean = false,   // INTEGER (female_important)
    val maleImportant: Boolean = false      // INTEGER (male_important)
)

@Serializable
object ADSSCREEN

@Serializable
object  PRIVACYPOLICYSCREEN
