package com.scrymz.bitebuddy.presentation.screens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.scrymz.bitebuddy.data.entity.FoodTable
import com.scrymz.bitebuddy.data.local.model.model.Food
import com.scrymz.bitebuddy.presentation.navigation.routes.FOODINFOSCREEN
import com.scrymz.bitebuddy.presentation.utils.InterstitialAdHelper
import com.scrymz.bitebuddy.presentation.viewmodels.DatabaseOpnerViewModel
import com.scrymz.bitebuddy.presentation.viewmodels.FoodViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListOfAllFood(
    databaseOpenerViewModel: DatabaseOpnerViewModel = hiltViewModel(),
    foodViewModel: FoodViewModel = hiltViewModel(),
    navController: NavController
) {
    val copyDatabaseState by databaseOpenerViewModel.copyDatabase.collectAsState()
    val allFoodState by databaseOpenerViewModel.getAllDataFromDatabase.collectAsState()
    val searchState by databaseOpenerViewModel.searchFoodState.collectAsState()
    val upsertFoodState by foodViewModel.upsertFoodState.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("All") }
    var showAddFoodDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val activity = context as? Activity

    // ✅ Start DB copy
    LaunchedEffect(Unit) {
        databaseOpenerViewModel.copyDatabase()
        // Preload interstitial ad
        activity?.let { InterstitialAdHelper.loadAd(it) }
    }

    // ✅ Trigger data load when copy is done
    LaunchedEffect(copyDatabaseState) {
        if (!copyDatabaseState.isLoading && copyDatabaseState.error.isEmpty()) {
            databaseOpenerViewModel.getAllDataFromDatabase()
        }
    }

    // Show snackbar on upsert success/error
    LaunchedEffect(upsertFoodState) {
        if (upsertFoodState.message.isNotEmpty()) {
            scope.launch {
                snackbarHostState.showSnackbar(upsertFoodState.message)
                databaseOpenerViewModel.getAllDataFromDatabase() // Refresh list
            }
        }
        if (upsertFoodState.error.isNotEmpty()) {
            scope.launch {
                snackbarHostState.showSnackbar("Error: ${upsertFoodState.error}")
            }
        }
    }

    // Build dynamic type options whenever data changes
    val typeOptions = remember(allFoodState.data) {
        listOf("All") + allFoodState.data.mapNotNull { it.type }.distinct().sorted()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddFoodDialog = true
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Custom Food")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.background)
            ) {

                // 🔍 Search bar with themed colors
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        databaseOpenerViewModel.searchFood(it)
                    },
                    label = { Text("Search Food...", color = MaterialTheme.colorScheme.primary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    singleLine = true

                )

                // 🎯 Type Filter Chips (replaces dropdown)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    typeOptions.forEach { type ->
                        FilterChip(
                            selected = selectedType == type,
                            onClick = { selectedType = type },
                            label = { Text(text =if (type == "All") "All Types" else type ,
                                color = MaterialTheme.colorScheme.primary

                            ) },
                            border = BorderStroke(
                                width = 1.dp, brush = Brush.linearGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.tertiary
                                    )


                                )
                            )
                        )
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    when {
                        copyDatabaseState.isLoading || allFoodState.isLoading || searchState.isLoading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = MaterialTheme.colorScheme.primary // ✅ Themed loader
                            )
                        }

                        copyDatabaseState.error.isNotEmpty() -> {
                            Text(
                                text = copyDatabaseState.error,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        allFoodState.error.isNotEmpty() -> {
                            Text(
                                text = allFoodState.error,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        // ✅ Show search results if user typed something
                        searchQuery.isNotBlank() -> {
                            val filteredSearchData = if (selectedType == "All") {
                                searchState.data
                            } else {
                                searchState.data.filter { it.type == selectedType }
                            }

                            if (filteredSearchData.isNotEmpty()) {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(filteredSearchData) { food -> FoodCard(food, navController = navController) }
                                }
                            } else {
                                Text(
                                    text = "No matching food found",
                                    color = MaterialTheme.colorScheme.secondary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }

                        // ✅ Otherwise show all food
                        allFoodState.data.isNotEmpty() -> {
                            val filteredData = if (selectedType == "All") {
                                allFoodState.data
                            } else {
                                allFoodState.data.filter { it.type == selectedType }
                            }

                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(filteredData) { food -> FoodCard(food, navController) }
                            }
                        }

                        else -> {
                            Text(
                                text = "No Food Data Found",
                                color = MaterialTheme.colorScheme.secondary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }

            // Banner Ad at bottom
            BannerAds()
        }

        // Add Food Dialog
        if (showAddFoodDialog) {
            AddFoodDialog(
                activity = activity,
                onDismiss = { showAddFoodDialog = false },
                onSave = { foodTable ->
                    foodViewModel.upsertFood(foodTable)
                    showAddFoodDialog = false
                }
            )
        }
    }
}

@Composable
fun FoodCard(food: Food, navController: NavController) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        border = BorderStroke(2.dp, brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.tertiary
            )
        )),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface // Matches theme surface color,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(
                    FOODINFOSCREEN(
                        id = food.id,
                        type = food.type,
                        foodname = food.foodname,
                        pergram = food.pergram,
                        calories = food.calories,
                        protein = food.protein,
                        calcium = food.calcium,
                        iron = food.iron,
                        magnesium = food.magnesium,
                        vitA = food.vitA,
                        vitB12 = food.vitB12,
                        vitC = food.vitC,
                        vitD = food.vitD,
                        safeInPregnancy = food.safeInPregnancy,
                        menstrualSafe = food.menstrualSafe,
                        femaleImportant = food.femaleImportant,
                        maleImportant = food.maleImportant
                    )
                )

            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // 🍽 Food Name — Primary Color (Green)
            Text(
                text = food.foodname,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 🏷 Food Type — Secondary Color (Lighter Green)
            Text(
                text = "Type: ${food.type ?: "Unknown"}",
                color = MaterialTheme.colorScheme.secondary
            )

            // 🔥 Calories — Tertiary Color (Orange Accent)
            Text(
                text = "Calories: ${food.calories ?: "N/A"}",
                color = MaterialTheme.colorScheme.tertiary
            )

            // 🥩 Protein — Keep neutral but slightly dimmed
            Text(
                text = "Protein: ${food.protein ?: "N/A"} g",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun AddFoodDialog(
    activity: Activity?,
    onDismiss: () -> Unit,
    onSave: (FoodTable) -> Unit
) {
    var foodName by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var calcium by remember { mutableStateOf("") }
    var iron by remember { mutableStateOf("") }
    var magnesium by remember { mutableStateOf("") }
    var vitA by remember { mutableStateOf("") }
    var vitB12 by remember { mutableStateOf("") }
    var vitC by remember { mutableStateOf("") }
    var vitD by remember { mutableStateOf("") }
    var safeInPregnancy by remember { mutableStateOf(false) }
    var menstrualSafe by remember { mutableStateOf(false) }
    var femaleImportant by remember { mutableStateOf(false) }
    var maleImportant by remember { mutableStateOf(false) }

    // Get current time and date
    val currentTimeOfDay = remember {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when {
            hour < 12 -> "Morning"
            hour < 17 -> "Afternoon"
            hour < 21 -> "Evening"
            else -> "Night"
        }
    }
    val currentDate = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().time)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Add Custom Food",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Food Name (Required)
                OutlinedTextField(
                    value = foodName,
                    onValueChange = { foodName = it },
                    label = { Text("Food Name *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                // Type
                OutlinedTextField(
                    value = type,
                    onValueChange = { type = it },
                    label = { Text("Type") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                // Quantity
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Quantity (g)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Text("Nutrition", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = calories,
                        onValueChange = { calories = it.filter { c -> c.isDigit() || c == '.' } },
                        label = { Text("Calories") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    OutlinedTextField(
                        value = protein,
                        onValueChange = { protein = it.filter { c -> c.isDigit() || c == '.' } },
                        label = { Text("Protein") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = calcium,
                        onValueChange = { calcium = it.filter { c -> c.isDigit() || c == '.' } },
                        label = { Text("Calcium") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    OutlinedTextField(
                        value = iron,
                        onValueChange = { iron = it.filter { c -> c.isDigit() || c == '.' } },
                        label = { Text("Iron") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = magnesium,
                        onValueChange = { magnesium = it.filter { c -> c.isDigit() || c == '.' } },
                        label = { Text("Magnesium") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    OutlinedTextField(
                        value = vitA,
                        onValueChange = { vitA = it.filter { c -> c.isDigit() || c == '.' } },
                        label = { Text("Vit A") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = vitB12,
                        onValueChange = { vitB12 = it.filter { c -> c.isDigit() || c == '.' } },
                        label = { Text("Vit B12") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    OutlinedTextField(
                        value = vitC,
                        onValueChange = { vitC = it.filter { c -> c.isDigit() || c == '.' } },
                        label = { Text("Vit C") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                }

                OutlinedTextField(
                    value = vitD,
                    onValueChange = { vitD = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Vit D") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Text("Health Flags", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { safeInPregnancy = !safeInPregnancy }) {
                    Checkbox(checked = safeInPregnancy, onCheckedChange = { safeInPregnancy = it })
                    Spacer(Modifier.width(4.dp))
                    Text("Safe in Pregnancy")
                }

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { menstrualSafe = !menstrualSafe }) {
                    Checkbox(checked = menstrualSafe, onCheckedChange = { menstrualSafe = it })
                    Spacer(Modifier.width(4.dp))
                    Text("Menstrual Safe")
                }

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { femaleImportant = !femaleImportant }) {
                    Checkbox(checked = femaleImportant, onCheckedChange = { femaleImportant = it })
                    Spacer(Modifier.width(4.dp))
                    Text("Female Important")
                }

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { maleImportant = !maleImportant }) {
                    Checkbox(checked = maleImportant, onCheckedChange = { maleImportant = it })
                    Spacer(Modifier.width(4.dp))
                    Text("Male Important")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (foodName.isNotBlank()) {
                        val foodTable = FoodTable(
                            type = type.ifBlank { null },
                            foodname = foodName,
                            gramConsumed = quantity.toDoubleOrNull() ?: 0.0,
                            calories = calories.toDoubleOrNull() ?: 0.0,
                            protein = protein.toDoubleOrNull() ?: 0.0,
                            calcium = calcium.toDoubleOrNull() ?: 0.0,
                            iron = iron.toDoubleOrNull() ?: 0.0,
                            magnesium = magnesium.toDoubleOrNull() ?: 0.0,
                            vitA = vitA.toDoubleOrNull() ?: 0.0,
                            vitB12 = vitB12.toDoubleOrNull() ?: 0.0,
                            vitC = vitC.toDoubleOrNull() ?: 0.0,
                            vitD = vitD.toDoubleOrNull() ?: 0.0,
                            safeInPregnancy = safeInPregnancy,
                            menstrualSafe = menstrualSafe,
                            femaleImportant = femaleImportant,
                            maleImportant = maleImportant,
                            timeConsumed = currentTimeOfDay,
                            dateConsumed = currentDate
                        )

                        // Show ad first, then save
                        activity?.let { act ->
                            InterstitialAdHelper.showAd(
                                activity = act,
                                onAdDismissed = {
                                    // Ad was shown and dismissed, now save
                                    onSave(foodTable)
                                },
                                onAdFailed = {
                                    // Ad failed to load or show, directly save
                                    onSave(foodTable)
                                }
                            )
                        } ?: run {
                            // No activity context, directly save
                            onSave(foodTable)
                        }
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

