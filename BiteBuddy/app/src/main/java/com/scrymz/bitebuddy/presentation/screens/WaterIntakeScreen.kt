package com.scrymz.bitebuddy.presentation.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.scrymz.bitebuddy.Constants.Constants
import com.scrymz.bitebuddy.data.entity.WaterIntake
import com.scrymz.bitebuddy.presentation.viewmodels.WaterIntakeViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.div

@Composable
fun WaterIntakeScreen(
    viewModel: WaterIntakeViewModel = hiltViewModel()
) {
    val allWaterIntakesState by viewModel.allWaterIntakesState.collectAsState()
    val waterIntakesByDateState by viewModel.waterIntakesByDateState.collectAsState()
    val waterIntakesByMonthState by viewModel.waterIntakesByMonthState.collectAsState()
    val waterIntakesByYearState by viewModel.waterIntakesByYearState.collectAsState()
    val upsertState by viewModel.upsertWaterIntakeState.collectAsState()
    val deleteState by viewModel.deleteWaterIntakeState.collectAsState()
    val totalWaterState by viewModel.totalWaterState.collectAsState()
    val averageWaterState by viewModel.averageWaterState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var editingWaterIntake by remember { mutableStateOf<WaterIntake?>(null) }
    var showFilterDialog by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("All") }
    var selectedDate by remember { mutableStateOf("") }
    var selectedMonth by remember { mutableStateOf(0) }
    var selectedYear by remember { mutableStateOf(0) }

    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val calendar = Calendar.getInstance()

    // Initialize with today's date
    LaunchedEffect(Unit) {
        val today = dateFormatter.format(calendar.time)
        selectedDate = today
        selectedMonth = calendar.get(Calendar.MONTH) + 1
        selectedYear = calendar.get(Calendar.YEAR)

        viewModel.getAllWaterIntakesDescending()
        viewModel.getTotalWaterByDate(today)
        viewModel.getAverageWaterByMonth(selectedMonth, selectedYear)
    }

    // Real-time update after operations
    LaunchedEffect(upsertState.message, deleteState.message) {
        if (upsertState.message.isNotEmpty() || deleteState.message.isNotEmpty()) {
            when (selectedFilter) {
                "All" -> viewModel.getAllWaterIntakesDescending()
                "Today" -> viewModel.getWaterIntakesByDate(selectedDate)
                "This Month" -> viewModel.getWaterIntakesByMonth(selectedMonth, selectedYear)
                "This Year" -> viewModel.getWaterIntakesByYear(selectedYear)
            }
            viewModel.getTotalWaterByDate(selectedDate)
            viewModel.getAverageWaterByMonth(selectedMonth, selectedYear)
        }
    }

    val displayData = when (selectedFilter) {
        "Today" -> waterIntakesByDateState.data
        "This Month" -> waterIntakesByMonthState.data
        "This Year" -> waterIntakesByYearState.data
        else -> allWaterIntakesState.data
    }

    val isLoading = when (selectedFilter) {
        "Today" -> waterIntakesByDateState.isLoading
        "This Month" -> waterIntakesByMonthState.isLoading
        "This Year" -> waterIntakesByYearState.isLoading
        else -> allWaterIntakesState.isLoading
    }

    val error = when (selectedFilter) {
        "Today" -> waterIntakesByDateState.error
        "This Month" -> waterIntakesByMonthState.error
        "This Year" -> waterIntakesByYearState.error
        else -> allWaterIntakesState.error
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editingWaterIntake = null
                    showDialog = true
                },
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 60.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Water Intake")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Header with Stats
                Text(
                    "Water Tracker",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(16.dp))

                // Stats Cards Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Today's Total Card
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.WaterDrop,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                            if (totalWaterState.isLoading) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            } else {
                                Text(
                                    "${totalWaterState.value.toInt()}",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text("ml Today", style = MaterialTheme.typography.bodySmall)

                                // Progress Bar
                                Spacer(Modifier.height(8.dp))
                                val progress = (totalWaterState.value / 2000f).coerceIn(0.0, 1.0).toFloat()
                                LinearProgressIndicator(
                                    progress = { progress },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    color = MaterialTheme.colorScheme.primary,
                                )

                                Text(
                                    "${(progress * 100).toInt()}% of 2000ml",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }

                    // Monthly Average Card
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                            if (averageWaterState.isLoading) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            } else {
                                Text(
                                    "${averageWaterState.value.toInt()}",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Text("ml/day Avg", style = MaterialTheme.typography.bodySmall)
                                Text(
                                    "This Month",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Filter Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Filter:", fontWeight = FontWeight.Medium)

                    FilterChip(
                        selected = selectedFilter == "All",
                        onClick = {
                            selectedFilter = "All"
                            viewModel.getAllWaterIntakesDescending()
                        },
                        label = { Text("All") }
                    )

                    FilterChip(
                        selected = selectedFilter == "Today",
                        onClick = {
                            selectedFilter = "Today"
                            viewModel.getWaterIntakesByDate(selectedDate)
                        },
                        label = { Text("Today") }
                    )

                    FilterChip(
                        selected = selectedFilter == "This Month",
                        onClick = {
                            selectedFilter = "This Month"
                            viewModel.getWaterIntakesByMonth(selectedMonth, selectedYear)
                        },
                        label = { Text("Month") }
                    )

                    FilterChip(
                        selected = selectedFilter == "This Year",
                        onClick = {
                            selectedFilter = "This Year"
                            viewModel.getWaterIntakesByYear(selectedYear)
                        },
                        label = { Text("Year") }
                    )

                    Spacer(Modifier.weight(1f))

                    IconButton(
                        onClick = {
                            when (selectedFilter) {
                                "All" -> viewModel.getAllWaterIntakesDescending()
                                "Today" -> viewModel.getWaterIntakesByDate(selectedDate)
                                "This Month" -> viewModel.getWaterIntakesByMonth(selectedMonth, selectedYear)
                                "This Year" -> viewModel.getWaterIntakesByYear(selectedYear)
                            }
                            viewModel.getTotalWaterByDate(selectedDate)
                            viewModel.getAverageWaterByMonth(selectedMonth, selectedYear)
                        }
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Content
                when {
                    isLoading -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    error.isNotEmpty() -> {
                        ErrorView(message = error) {
                            when (selectedFilter) {
                                "All" -> viewModel.getAllWaterIntakesDescending()
                                "Today" -> viewModel.getWaterIntakesByDate(selectedDate)
                                "This Month" -> viewModel.getWaterIntakesByMonth(selectedMonth, selectedYear)
                                "This Year" -> viewModel.getWaterIntakesByYear(selectedYear)
                            }
                        }
                    }
                    displayData.isEmpty() -> {
                        EmptyView(message = "No water intake recorded for $selectedFilter")
                    }
                    else -> {
                        Text(
                            "Water Intake Records (${displayData.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(8.dp))
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(displayData) { waterIntake ->
                                WaterIntakeItem(
                                    waterIntake = waterIntake,
                                    onEdit = {
                                        editingWaterIntake = waterIntake
                                        showDialog = true
                                    },
                                    onDelete = { viewModel.deleteWaterIntake(waterIntake) }
                                )
                            }
                        }
                    }
                }
            }

            // Banner Ad at bottom
            BannerAds()
        }
    }

    if (showDialog) {
        AddEditWaterIntakeDialog(
            waterIntake = editingWaterIntake,
            onDismiss = { showDialog = false },
            onSave = { newWaterIntake ->
                viewModel.upsertWaterIntake(newWaterIntake)
                showDialog = false
            }
        )
    }
}

@Composable
fun WaterIntakeItem(
    waterIntake: WaterIntake,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.WaterDrop,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Column {
                    Text(
                        "${waterIntake.amountMl.toInt()} ml",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        "${waterIntake.date} at ${waterIntake.time}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            waterIntake.timeOfDay,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        waterIntake.containerType?.let {
                            Text("•", style = MaterialTheme.typography.labelSmall)
                            Text(it, style = MaterialTheme.typography.labelSmall)
                        }
                        waterIntake.temperature?.let {
                            Text("•", style = MaterialTheme.typography.labelSmall)
                            Text(it, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun AddEditWaterIntakeDialog(
    waterIntake: WaterIntake?,
    onDismiss: () -> Unit,
    onSave: (WaterIntake) -> Unit
) {
    var date by remember { mutableStateOf(waterIntake?.date ?: "") }
    var time by remember { mutableStateOf(waterIntake?.time ?: "") }
    var amountMl by remember { mutableStateOf(waterIntake?.amountMl?.toString() ?: "") }
    var timeOfDay by remember { mutableStateOf(waterIntake?.timeOfDay ?: Constants.MORNING) }
    var containerType by remember { mutableStateOf(waterIntake?.containerType ?: "") }
    var temperature by remember { mutableStateOf(waterIntake?.temperature ?: "") }
    var notes by remember { mutableStateOf(waterIntake?.notes ?: "") }

    val timesOfDay = listOf(Constants.MORNING, Constants.AFTERNOON, Constants.EVENING, Constants.NIGHT)
    val containerTypes = listOf("", "Glass", "Bottle", "Cup", "Jug")
    val temperatures = listOf("", "Cold", "Room Temp", "Warm", "Hot")

    val context = LocalContext.current
    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val timeFormatter = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }

    fun openDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, day ->
                val cal = Calendar.getInstance()
                cal.set(year, month, day)
                date = dateFormatter.format(cal.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    fun openTimePicker() {
        val calendar = Calendar.getInstance()
        android.app.TimePickerDialog(
            context,
            { _, hour, minute ->
                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                time = timeFormatter.format(cal.time)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    LaunchedEffect(Unit) {
        if (waterIntake == null) {
            val now = Calendar.getInstance()
            date = dateFormatter.format(now.time)
            time = timeFormatter.format(now.time)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (waterIntake == null) "Add Water Intake" else "Edit Water Intake") },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = date,
                        onValueChange = {},
                        label = { Text("Date") },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { openDatePicker() }) {
                                Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = time,
                        onValueChange = {},
                        label = { Text("Time") },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { openTimePicker() }) {
                                Icon(Icons.Default.DateRange, contentDescription = "Pick Time")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = amountMl,
                        onValueChange = { amountMl = it },
                        label = { Text("Amount (ml)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    DropdownSelector(
                        label = "Time of Day",
                        options = timesOfDay,
                        selected = timeOfDay,
                        onSelectedChange = { timeOfDay = it }
                    )
                }

                item {
                    DropdownSelector(
                        label = "Container Type (Optional)",
                        options = containerTypes,
                        selected = containerType,
                        onSelectedChange = { containerType = it }
                    )
                }

                item {
                    DropdownSelector(
                        label = "Temperature (Optional)",
                        options = temperatures,
                        selected = temperature,
                        onSelectedChange = { temperature = it }
                    )
                }

                item {
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Notes (Optional)") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (date.isNotBlank() && time.isNotBlank() && amountMl.isNotBlank()) {
                        val calendar = Calendar.getInstance()
                        calendar.time = dateFormatter.parse(date)!!
                        onSave(
                            WaterIntake(
                                id = waterIntake?.id ?: 0,
                                date = date,
                                time = time,
                                amountMl = amountMl.toDoubleOrNull() ?: 0.0,
                                timeOfDay = timeOfDay,
                                containerType = containerType.ifBlank { null },
                                temperature = temperature.ifBlank { null },
                                month = calendar.get(Calendar.MONTH) + 1,
                                year = calendar.get(Calendar.YEAR),
                                notes = notes.ifBlank { null }
                            )
                        )
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}