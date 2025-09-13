package com.scrymz.bitebuddy.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.scrymz.bitebuddy.Constants.Constants
import com.scrymz.bitebuddy.data.entity.FoodTable
import com.scrymz.bitebuddy.presentation.viewmodels.FoodViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun FoodInfoScreen(
//    navController: NavController,
//    id: Int = 0,
//    type: String? = null,
//    foodname: String,
//    pergram: Double? = null,
//    calories: Double? = null,
//    protein: Double? = null,
//    calcium: Double? = null,
//    iron: Double? = null,
//    magnesium: Double? = null,
//    vitA: Double? = null,
//    vitB12: Double? = null,
//    vitC: Double? = null,
//    vitD: Double? = null,
//    safeInPregnancy: Boolean = false,
//    menstrualSafe: Boolean = false,
//    femaleImportant: Boolean = false,
//    maleImportant: Boolean = false
//) {
//    // 🟢 1. State for user-entered quantity
//    var quantityText by remember { mutableStateOf(pergram?.toInt()?.toString() ?: "100") }
//
//    // Convert quantity safely
//    val quantity = quantityText.toDoubleOrNull() ?: 0.0
//    val baseGram = pergram ?: 100.0
//    val multiplier = if (baseGram > 0) quantity / baseGram else 1.0
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(foodname, fontWeight = FontWeight.Bold) },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
//    ) { padding ->
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            // 🔢 Quantity Input
//            item {
//                OutlinedTextField(
//                    value = quantityText,
//                    onValueChange = { quantityText = it },
//                    label = { Text("Enter Quantity (g)") },
//                    modifier = Modifier.fillMaxWidth(),
//                    singleLine = true,
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                )
//            }
//
//            // 🔹 Basic Info
//            item { InfoCard(title = "Type", value = type ?: "Unknown") }
//            item { InfoCard(title = "Base Per Gram", value = baseGram.toString()) }
//
//            // 🔹 Nutrients (calculated dynamically)
//            item { SectionHeader("Nutrients (for $quantity g)") }
//            item { InfoCard("Calories", (calories?.times(multiplier))?.format() ?: "N/A") }
//            item { InfoCard("Protein", (protein?.times(multiplier))?.format() ?: "N/A g") }
//            item { InfoCard("Calcium", (calcium?.times(multiplier))?.format() ?: "N/A mg") }
//            item { InfoCard("Iron", (iron?.times(multiplier))?.format() ?: "N/A mg") }
//            item { InfoCard("Magnesium", (magnesium?.times(multiplier))?.format() ?: "N/A mg") }
//
//            // 🔹 Vitamins
//            item { SectionHeader("Vitamins") }
//            item { InfoCard("Vitamin A", (vitA?.times(multiplier))?.format() ?: "N/A") }
//            item { InfoCard("Vitamin B12", (vitB12?.times(multiplier))?.format() ?: "N/A") }
//            item { InfoCard("Vitamin C", (vitC?.times(multiplier))?.format() ?: "N/A") }
//            item { InfoCard("Vitamin D", (vitD?.times(multiplier))?.format() ?: "N/A") }
//
//            // 🔹 Health Tags
//            item { SectionHeader("Health Info") }
//            item { BooleanChip("Safe in Pregnancy", safeInPregnancy) }
//            item { BooleanChip("Menstrual Safe", menstrualSafe) }
//            item { BooleanChip("Important for Female", femaleImportant) }
//            item { BooleanChip("Important for Male", maleImportant) }
//        }
//    }
//}
//
@Composable
fun InfoCard(title: String, value: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, fontWeight = FontWeight.SemiBold)
            Text(text = value, color = Color.Gray)
        }
    }
}

@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun BooleanChip(label: String, isTrue: Boolean) {
    val bgColor = if (isTrue) Color(0xFF4CAF50) else Color(0xFFE57373)
    val textColor = Color.White
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor, RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Text(text = "$label: ${if (isTrue) "Yes" else "No"}", color = textColor)
    }
}

// 🔧 Extension function for formatting numbers to 2 decimal places
fun Double.format(): String = String.format("%.2f", this)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodInfoScreen(
    navController: NavController,
    viewModel: FoodViewModel = hiltViewModel(), // ✅ Injected ViewModel
    id: Int = 0,
    type: String? = null,
    foodname: String,
    pergram: Double? = null,
    calories: Double? = null,
    protein: Double? = null,
    calcium: Double? = null,
    iron: Double? = null,
    magnesium: Double? = null,
    vitA: Double? = null,
    vitB12: Double? = null,
    vitC: Double? = null,
    vitD: Double? = null,
    safeInPregnancy: Boolean = false,
    menstrualSafe: Boolean = false,
    femaleImportant: Boolean = false,
    maleImportant: Boolean = false
) {
    // 🟢 1. State for user-entered quantity
    var quantityText by remember { mutableStateOf(pergram?.toInt()?.toString() ?: "100") }
    val quantity = quantityText.toDoubleOrNull() ?: 0.0
    val baseGram = pergram ?: 100.0
    val multiplier = if (baseGram > 0) quantity / baseGram else 1.0

    // 🟢 2. Auto-select time of day (you can later use a dropdown if you want)
    val currentTimeOfDay = remember { getCurrentMealTime() }
    val currentDate = remember { getTodayDate() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(foodname, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val foodEntity = FoodTable(
                        type = type,
                        foodname = foodname,
                        gramConsumed = quantity,
                        calories = calories?.times(multiplier),
                        protein = protein?.times(multiplier),
                        calcium = calcium?.times(multiplier),
                        iron = iron?.times(multiplier),
                        magnesium = magnesium?.times(multiplier),
                        vitA = vitA?.times(multiplier),
                        vitB12 = vitB12?.times(multiplier),
                        vitC = vitC?.times(multiplier),
                        vitD = vitD?.times(multiplier),
                        safeInPregnancy = safeInPregnancy,
                        menstrualSafe = menstrualSafe,
                        femaleImportant = femaleImportant,
                        maleImportant = maleImportant,
                        timeConsumed = currentTimeOfDay,
                        dateConsumed = currentDate
                    )
                    viewModel.upsertFood(foodEntity)
                    navController.popBackStack() // Go back after adding
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add to Database")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                OutlinedTextField(
                    value = quantityText,
                    onValueChange = { quantityText = it },
                    label = { Text("Enter Quantity (g)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            item { InfoCard(title = "Type", value = type ?: "Unknown") }
            item { InfoCard(title = "Base Per Gram", value = baseGram.toString()) }

            item { SectionHeader("Nutrients (for $quantity g)") }
            item { InfoCard("Calories", (calories?.times(multiplier))?.format() ?: "N/A") }
            item { InfoCard("Protein", (protein?.times(multiplier))?.format() ?: "N/A g") }
            item { InfoCard("Calcium", (calcium?.times(multiplier))?.format() ?: "N/A mg") }
            item { InfoCard("Iron", (iron?.times(multiplier))?.format() ?: "N/A mg") }
            item { InfoCard("Magnesium", (magnesium?.times(multiplier))?.format() ?: "N/A mg") }

            item { SectionHeader("Vitamins") }
            item { InfoCard("Vitamin A", (vitA?.times(multiplier))?.format() ?: "N/A") }
            item { InfoCard("Vitamin B12", (vitB12?.times(multiplier))?.format() ?: "N/A") }
            item { InfoCard("Vitamin C", (vitC?.times(multiplier))?.format() ?: "N/A") }
            item { InfoCard("Vitamin D", (vitD?.times(multiplier))?.format() ?: "N/A") }

            item { SectionHeader("Health Info") }
            item { BooleanChip("Safe in Pregnancy", safeInPregnancy) }
            item { BooleanChip("Menstrual Safe", menstrualSafe) }
            item { BooleanChip("Important for Female", femaleImportant) }
            item { BooleanChip("Important for Male", maleImportant) }
        }
    }
}
fun getCurrentMealTime(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when {
        hour in 5..11 -> Constants.MORNING
        hour in 12..16 -> Constants.AFTERNOON
        hour in 17..20 -> Constants.EVENING
        hour in 21..23 -> Constants.NIGHT
        else -> Constants.MIDNIGHTCRAVINGS
    }
}

fun getTodayDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date())
}
