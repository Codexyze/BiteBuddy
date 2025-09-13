package com.scrymz.bitebuddy.presentation.screens
import android.app.ProgressDialog.show
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.scrymz.bitebuddy.Constants.Constants
import com.scrymz.bitebuddy.data.entity.MenstrualPeriod
import com.scrymz.bitebuddy.presentation.viewmodels.MenstrualViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun MenstrualScreen(
    viewModel: MenstrualViewModel = hiltViewModel()
) {
    val allPeriodsState by viewModel.allPeriodsState.collectAsState()
    val upsertState by viewModel.upsertPeriodState.collectAsState()
    val deleteState by viewModel.deletePeriodState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var editingPeriod by remember { mutableStateOf<MenstrualPeriod?>(null) }

    // Load data initially
    LaunchedEffect(Unit) {
        viewModel.getAllPeriodsDescending()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editingPeriod = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Filters
            FilterRow(
                onFilterByMonth = { month, year -> viewModel.getPeriodsByMonth(month, year) },
                onFilterByPain = { viewModel.getPeriodsByPainLevel(it) },
                onFilterByTimeOfDay = { viewModel.getPeriodsByTimeOfDay(it) },
                onResetFilters = { viewModel.getAllPeriodsDescending() }
            )

            Spacer(Modifier.height(12.dp))

            when {
                allPeriodsState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                allPeriodsState.error.isNotEmpty() -> {
                    ErrorView(message = allPeriodsState.error) {
                        viewModel.getAllPeriodsDescending()
                    }
                }
                allPeriodsState.data.isEmpty() -> {
                    EmptyView(message = "No periods recorded yet.")
                }
                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(allPeriodsState.data) { period ->
                            PeriodItem(
                                period = period,
                                onEdit = {
                                    editingPeriod = period
                                    showDialog = true
                                },
                                onDelete = { viewModel.deletePeriod(period) }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddEditPeriodDialog(
            period = editingPeriod,
            onDismiss = { showDialog = false },
            onSave = { newPeriod ->
                viewModel.upsertPeriod(newPeriod)
                showDialog = false
            }
        )
    }

    LaunchedEffect(upsertState.message, deleteState.message) {
        if (upsertState.message.isNotEmpty() || deleteState.message.isNotEmpty()) {
            viewModel.getAllPeriodsDescending()
        }
    }
}

@Composable
fun FilterRow(
    onFilterByMonth: (Int, Int) -> Unit,
    onFilterByPain: (String) -> Unit,
    onFilterByTimeOfDay: (String) -> Unit,
    onResetFilters: () -> Unit
) {
//    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//        Button(onClick = { onFilterByMonth(9, 2025) }) { Text("This Month") }
//        Button(onClick = { onFilterByPain("Severe") }) { Text("Severe Pain") }
//        Button(onClick = { onFilterByTimeOfDay("Morning") }) { Text("Morning") }
//        OutlinedButton(onClick = { onResetFilters() }) { Text("Reset") }
//    }
}

@Composable
fun PeriodItem(
    period: MenstrualPeriod,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text("${period.startDate} → ${period.endDate ?: "Ongoing"}", fontWeight = FontWeight.Bold)
            Text("Pain: ${period.painLevel} | Flow: ${period.flowIntensity}")
            Text("Time: ${period.timeAppeared}")
            period.symptoms?.let { Text("Symptoms: $it") }
            period.notes?.let { Text("Notes: $it") }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}


@Composable
fun EmptyView(message: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(message, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Error: $message", color = Color.Red)
        Button(onClick = onRetry) { Text("Retry") }
    }
}
@Composable
fun AddEditPeriodDialog(
    period: MenstrualPeriod?,
    onDismiss: () -> Unit,
    onSave: (MenstrualPeriod) -> Unit
) {
    // State holders
    var startDate by remember { mutableStateOf(period?.startDate ?: "") }
    var endDate by remember { mutableStateOf(period?.endDate ?: "") }
    var painLevel by remember { mutableStateOf(period?.painLevel ?: "Normal") }
    var timeAppeared by remember { mutableStateOf(period?.timeAppeared ?: "Morning") }
    var flowIntensity by remember { mutableStateOf(period?.flowIntensity ?: "Normal") }
    var symptoms by remember { mutableStateOf(period?.symptoms ?: "") }
    var notes by remember { mutableStateOf(period?.notes ?: "") }

    // Dropdown options
    val painLevels = listOf("Mild", "Normal", "Severe")
    val timesOfDay = listOf("Morning", "Afternoon", "Evening", "Night")
    val flowOptions = listOf("Light", "Normal", "Heavy")

    // Date pickers
    val context = LocalContext.current
    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    fun openDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        android.app.DatePickerDialog(
            context,
            { _, year, month, day ->
                val cal = Calendar.getInstance()
                cal.set(year, month, day)
                onDateSelected(dateFormatter.format(cal.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (period == null) "Add Period" else "Edit Period") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Start Date Picker
                OutlinedTextField(
                    value = startDate,
                    onValueChange = {},
                    label = { Text("Start Date") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { openDatePicker { startDate = it } }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // End Date Picker (Optional)
                OutlinedTextField(
                    value = endDate,
                    onValueChange = {},
                    label = { Text("End Date (Optional)") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { openDatePicker { endDate = it } }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // Pain Level Dropdown
                DropdownSelector(
                    label = "Pain Level",
                    options = painLevels,
                    selected = painLevel,
                    onSelectedChange = { painLevel = it }
                )

                // Time of Day Dropdown
                DropdownSelector(
                    label = "Time of Day",
                    options = timesOfDay,
                    selected = timeAppeared,
                    onSelectedChange = { timeAppeared = it }
                )

                // Flow Intensity Dropdown
                DropdownSelector(
                    label = "Flow Intensity",
                    options = flowOptions,
                    selected = flowIntensity,
                    onSelectedChange = { flowIntensity = it }
                )

                OutlinedTextField(
                    value = symptoms,
                    onValueChange = { symptoms = it },
                    label = { Text("Symptoms (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (startDate.isNotBlank()) {
                        onSave(
                            MenstrualPeriod(
                                id = period?.id ?: 0,
                                startDate = startDate,
                                endDate = if (endDate.isBlank()) null else endDate,
                                painLevel = painLevel,
                                timeAppeared = timeAppeared,
                                flowIntensity = flowIntensity,
                                month = startDate.split("-")[1].toInt(),
                                year = startDate.split("-")[0].toInt(),
                                symptoms = symptoms.ifBlank { null },
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

@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selected: String,
    onSelectedChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Select")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelectedChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
