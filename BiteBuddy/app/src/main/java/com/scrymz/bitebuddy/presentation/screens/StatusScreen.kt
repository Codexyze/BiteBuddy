package com.scrymz.bitebuddy.presentation.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.scrymz.bitebuddy.data.entity.FoodTable
import com.scrymz.bitebuddy.presentation.states.GetAllFoodState
import com.scrymz.bitebuddy.presentation.states.GetByConsumedTimeState
import com.scrymz.bitebuddy.presentation.states.GetByDateState
import com.scrymz.bitebuddy.presentation.states.GetCaloriesState
import com.scrymz.bitebuddy.presentation.states.GetProteinState
import com.scrymz.bitebuddy.presentation.viewmodels.FoodViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusScreen(
    navController: NavController,
    viewModel: FoodViewModel = hiltViewModel()
) {
    val getAllFoodState by viewModel.getAllFoodState.collectAsState()
    val getByDateState by viewModel.getByDateState.collectAsState()
    val getByConsumedTimeState by viewModel.getByConsumedTimeState.collectAsState()
    val getProteinState by viewModel.getProteinState.collectAsState()
    val getCaloriesState by viewModel.getCaloriesState.collectAsState()
    val upsertFoodState by viewModel.upsertFoodState.collectAsState()
    val deleteFoodState by viewModel.deleteFoodState.collectAsState()

    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("All") }
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle delete state
    LaunchedEffect(deleteFoodState) {
        if (deleteFoodState.message.isNotEmpty()) {
            snackbarHostState.showSnackbar("Food deleted successfully")
            // Refresh data after successful deletion
            refreshData(viewModel, selectedDate, selectedTime)
        }
        if (deleteFoodState.error.isNotEmpty()) {
            snackbarHostState.showSnackbar("Error: ${deleteFoodState.error}")
        }
    }

    // Handle upsert state
    LaunchedEffect(upsertFoodState) {
        if (upsertFoodState.message.isNotEmpty()) {
            snackbarHostState.showSnackbar("Food updated successfully")
            // Refresh data after successful update
            refreshData(viewModel, selectedDate, selectedTime)
        }
        if (upsertFoodState.error.isNotEmpty()) {
            snackbarHostState.showSnackbar("Error: ${upsertFoodState.error}")
        }
    }

    // Load all food data on startup
    LaunchedEffect(Unit) {
        viewModel.getAllFood()
        // Also load today's protein and calories by default
        val today = getTodayDate()
        viewModel.getProtein(today)
        viewModel.getCalories(today)
    }

    // When date changes, load protein and calories for that date
    LaunchedEffect(selectedDate) {
        if (selectedDate.isNotEmpty()) {
            viewModel.getByDate(selectedDate)
            viewModel.getProtein(selectedDate)
            viewModel.getCalories(selectedDate)
        } else {
            // If no date selected, show today's data
            val today = getTodayDate()
            viewModel.getProtein(today)
            viewModel.getCalories(today)
        }
    }

    // When time filter changes
    LaunchedEffect(selectedTime) {
        if (selectedTime != "All") {
            viewModel.getByConsumedTime(selectedTime)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Food Dashboard", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // DATE FILTER
            OutlinedTextField(
                value = selectedDate,
                onValueChange = { selectedDate = it },
                label = { Text("Enter Date (yyyy-MM-dd)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Leave empty for today") }
            )

            Spacer(Modifier.height(12.dp))

            // TIME FILTER CHIPS
            TimeFilterRow(
                selectedTime = selectedTime,
                onTimeSelected = { selectedTime = it }
            )

            Spacer(Modifier.height(12.dp))

            // Summary Cards (Protein + Calories)
            Text(
                text = if (selectedDate.isNotEmpty()) "Summary for $selectedDate" else "Today's Summary",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(Modifier.height(8.dp))

            SummarySection(
                proteinState = getProteinState,
                caloriesState = getCaloriesState
            )

            Spacer(Modifier.height(16.dp))

            // Food Log Section
            Text(
                text = "Food Log",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(Modifier.height(8.dp))

            FoodListSection(
                selectedDate = selectedDate,
                selectedTime = selectedTime,
                allFoodState = getAllFoodState,
                byDateState = getByDateState,
                byTimeState = getByConsumedTimeState,
                viewModel = viewModel,
                isDeleting = deleteFoodState.isLoading,
                isUpdating = upsertFoodState.isLoading
            )
        }
    }
}

@Composable
fun SummarySection(proteinState: GetProteinState, caloriesState: GetCaloriesState) {
    when {
        proteinState.isLoading || caloriesState.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        proteinState.error.isNotEmpty() || caloriesState.error.isNotEmpty() -> {
            ErrorView(message = proteinState.error.ifEmpty { caloriesState.error })
        }
        else -> {
            SummaryRow(
                protein = proteinState.value,
                calories = caloriesState.value
            )
        }
    }
}

@Composable
fun FoodListSection(
    selectedDate: String,
    selectedTime: String,
    allFoodState: GetAllFoodState,
    byDateState: GetByDateState,
    byTimeState: GetByConsumedTimeState,
    viewModel: FoodViewModel,
    isDeleting: Boolean,
    isUpdating: Boolean
) {
    val foodList = when {
        selectedDate.isNotEmpty() && selectedTime != "All" -> {
            // If both date and time filter are applied, filter byDateState by time
            byDateState.data.filter { it.timeConsumed == selectedTime }
        }
        selectedDate.isNotEmpty() -> byDateState.data
        selectedTime != "All" -> byTimeState.data
        else -> allFoodState.data
    }

    when {
        allFoodState.isLoading || byDateState.isLoading || byTimeState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        allFoodState.error.isNotEmpty() || byDateState.error.isNotEmpty() || byTimeState.error.isNotEmpty() -> {
            ErrorView(message = allFoodState.error.ifEmpty {
                byDateState.error.ifEmpty { byTimeState.error }
            })
        }
        foodList.isEmpty() -> {
            EmptyView()
        }
        else -> {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(foodList) { food ->
                    FoodItemCard(
                        food = food,
                        onEdit = { updatedFood -> viewModel.upsertFood(updatedFood) },
                        onDelete = { foodToDelete -> viewModel.deleteFood(foodToDelete) },
                        isDeleting = isDeleting,
                        isUpdating = isUpdating
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorView(message: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Error, contentDescription = null, tint = Color.Red, modifier = Modifier.size(40.dp))
        Spacer(Modifier.height(8.dp))
        Text(text = message, color = Color.Red, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun EmptyView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Info, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(40.dp))
        Spacer(Modifier.height(8.dp))
        Text(text = "No food data available", color = Color.Gray)
    }
}

@Composable
fun TimeFilterRow(
    selectedTime: String,
    onTimeSelected: (String) -> Unit
) {
    val options = listOf("All", "Morning", "Afternoon", "Evening", "Night", "Snack")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEach { option ->
            FilterChip(
                selected = option == selectedTime,
                onClick = { onTimeSelected(option) },
                label = { Text(option) }
            )
        }
    }
}

@Composable
fun SummaryRow(protein: Double, calories: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SummaryCard(title = "Protein", value = "${protein.format()} g")
        SummaryCard(title = "Calories", value = "${calories.format()} kcal")
    }
}

@Composable
fun SummaryCard(title: String, value: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun FoodItemCard(
    food: FoodTable,
    onEdit: (FoodTable) -> Unit,
    onDelete: (FoodTable) -> Unit,
    isDeleting: Boolean,
    isUpdating: Boolean
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = food.foodname, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(4.dp))
                    Text(text = "Consumed: ${food.gramConsumed?.format() ?: "0.0"} g")
                    Text(text = "Calories: ${food.calories?.format() ?: "0.0"}")
                    Text(text = "Protein: ${food.protein?.format() ?: "0.0"} g")
                    Text(
                        text = "Time: ${food.timeConsumed.ifEmpty { "--" }} | Date: ${food.dateConsumed.ifEmpty { "--" }}",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                    )
                }

                Column {
                    IconButton(
                        onClick = { showEditDialog = true },
                        modifier = Modifier.size(32.dp),
                        enabled = !isUpdating && !isDeleting
                    ) {
                        if (isUpdating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    IconButton(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier.size(32.dp),
                        enabled = !isUpdating && !isDeleting
                    ) {
                        if (isDeleting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = Color.Red
                            )
                        } else {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.Red,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // Delete Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Food Entry") },
            text = { Text("Are you sure you want to delete '${food.foodname}'? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete(food)
                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Edit Dialog
    if (showEditDialog) {
        EditFoodDialog(
            food = food,
            onDismiss = { showEditDialog = false },
            onSave = { updatedFood ->
                onEdit(updatedFood)
                showEditDialog = false
            }
        )
    }
}

@Composable
fun EditFoodDialog(
    food: FoodTable,
    onDismiss: () -> Unit,
    onSave: (FoodTable) -> Unit
) {
    var gramConsumed by remember { mutableStateOf(food.gramConsumed?.toString() ?: "0") }
    var timeConsumed by remember { mutableStateOf(food.timeConsumed) }
    var dateConsumed by remember { mutableStateOf(food.dateConsumed) }
    var showTimeDropdown by remember { mutableStateOf(false) }

    val timeOptions = listOf("Morning", "Afternoon", "Evening", "Night", "Snack")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Food Entry") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = food.foodname,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleSmall
                )

                OutlinedTextField(
                    value = gramConsumed,
                    onValueChange = { gramConsumed = it },
                    label = { Text("Grams Consumed") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Time Dropdown
                Box {
                    OutlinedTextField(
                        value = timeConsumed,
                        onValueChange = { },
                        label = { Text("Time Consumed") },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { showTimeDropdown = !showTimeDropdown }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Time")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    DropdownMenu(
                        expanded = showTimeDropdown,
                        onDismissRequest = { showTimeDropdown = false }
                    ) {
                        timeOptions.forEach { time ->
                            DropdownMenuItem(
                                text = { Text(time) },
                                onClick = {
                                    timeConsumed = time
                                    showTimeDropdown = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = dateConsumed,
                    onValueChange = { dateConsumed = it },
                    label = { Text("Date (yyyy-MM-dd)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val newGrams = gramConsumed.toDoubleOrNull() ?: 0.0
                    val originalGrams = food.gramConsumed ?: 1.0
                    val multiplier = if (originalGrams > 0) newGrams / originalGrams else 1.0

                    val updatedFood = food.copy(
                        gramConsumed = newGrams,
                        timeConsumed = timeConsumed,
                        dateConsumed = dateConsumed,
                        // Recalculate nutrition values based on new gram amount
                        calories = food.calories?.times(multiplier),
                        protein = food.protein?.times(multiplier),
                        calcium = food.calcium?.times(multiplier),
                        iron = food.iron?.times(multiplier),
                        magnesium = food.magnesium?.times(multiplier),
                        vitA = food.vitA?.times(multiplier),
                        vitB12 = food.vitB12?.times(multiplier),
                        vitC = food.vitC?.times(multiplier),
                        vitD = food.vitD?.times(multiplier)
                    )
                    onSave(updatedFood)
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

// Utility function to refresh data
private fun refreshData(viewModel: FoodViewModel, selectedDate: String, selectedTime: String) {
    viewModel.getAllFood()

    val dateToUse = if (selectedDate.isNotEmpty()) selectedDate else getTodayDate()
    viewModel.getProtein(dateToUse)
    viewModel.getCalories(dateToUse)

    if (selectedDate.isNotEmpty()) {
        viewModel.getByDate(selectedDate)
    }

    if (selectedTime != "All") {
        viewModel.getByConsumedTime(selectedTime)
    }
}

