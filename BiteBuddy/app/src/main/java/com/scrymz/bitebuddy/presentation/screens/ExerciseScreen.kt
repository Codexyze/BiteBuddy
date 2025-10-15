//package com.scrymz.bitebuddy.presentation.screens
//
//import android.app.DatePickerDialog
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.ArrowDropDown
//import androidx.compose.material.icons.filled.CalendarMonth
//import androidx.compose.material.icons.filled.DateRange
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material.icons.filled.FilterList
//import androidx.compose.material.icons.filled.FitnessCenter
//import androidx.compose.material.icons.filled.LocalFireDepartment
//import androidx.compose.material.icons.filled.Refresh
//import androidx.compose.material.icons.filled.Timer
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.Button
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Checkbox
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.FilterChip
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.scrymz.bitebuddy.Constants.Constants
//import com.scrymz.bitebuddy.data.entity.Exercise
//import com.scrymz.bitebuddy.presentation.viewmodels.ExerciseViewModel
//import java.text.SimpleDateFormat
//import java.util.Calendar
//import java.util.Locale
//
//@Composable
//fun ExerciseScreen(
//    viewModel: ExerciseViewModel = hiltViewModel()
//) {
//    val allExercisesState by viewModel.allExercisesState.collectAsState()
//    val exercisesByDateState by viewModel.exercisesByDateState.collectAsState()
//    val exercisesByMonthState by viewModel.exercisesByMonthState.collectAsState()
//    val exercisesByYearState by viewModel.exercisesByYearState.collectAsState()
//    val exercisesByTypeState by viewModel.exercisesByTypeState.collectAsState()
//    val exercisesByIntensityState by viewModel.exercisesByIntensityState.collectAsState()
//    val upsertState by viewModel.upsertExerciseState.collectAsState()
//    val deleteState by viewModel.deleteExerciseState.collectAsState()
//    val totalCaloriesState by viewModel.totalCaloriesBurnedState.collectAsState()
//    val totalDurationState by viewModel.totalDurationState.collectAsState()
//
//    var showDialog by remember { mutableStateOf(false) }
//    var editingExercise by remember { mutableStateOf<Exercise?>(null) }
//    var selectedFilter by remember { mutableStateOf("All") }
//    var selectedDate by remember { mutableStateOf("") }
//    var selectedMonth by remember { mutableStateOf(0) }
//    var selectedYear by remember { mutableStateOf(0) }
//    var selectedType by remember { mutableStateOf("") }
//    var selectedIntensity by remember { mutableStateOf("") }
//
//    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
//    val calendar = Calendar.getInstance()
//
//    // Initialize with today's date
//    LaunchedEffect(Unit) {
//        val today = dateFormatter.format(calendar.time)
//        selectedDate = today
//        selectedMonth = calendar.get(Calendar.MONTH) + 1
//        selectedYear = calendar.get(Calendar.YEAR)
//
//        viewModel.getAllExercisesDescending()
//        viewModel.getTotalCaloriesBurnedByDate(today)
//        viewModel.getTotalDurationByDate(today)
//    }
//
//    // Real-time update after operations
//    LaunchedEffect(upsertState.message, deleteState.message) {
//        if (upsertState.message.isNotEmpty() || deleteState.message.isNotEmpty()) {
//            when (selectedFilter) {
//                "All" -> viewModel.getAllExercisesDescending()
//                "Today" -> viewModel.getExercisesByDate(selectedDate)
//                "This Month" -> viewModel.getExercisesByMonth(selectedMonth, selectedYear)
//                "This Year" -> viewModel.getExercisesByYear(selectedYear)
//                "By Type" -> if (selectedType.isNotEmpty()) viewModel.getExercisesByType(selectedType)
//                "By Intensity" -> if (selectedIntensity.isNotEmpty()) viewModel.getExercisesByIntensity(selectedIntensity)
//            }
//            viewModel.getTotalCaloriesBurnedByDate(selectedDate)
//            viewModel.getTotalDurationByDate(selectedDate)
//        }
//    }
//
//    val displayData = when (selectedFilter) {
//        "Today" -> exercisesByDateState.data
//        "This Month" -> exercisesByMonthState.data
//        "This Year" -> exercisesByYearState.data
//        "By Type" -> exercisesByTypeState.data
//        "By Intensity" -> exercisesByIntensityState.data
//        else -> allExercisesState.data
//    }
//
//    val isLoading = when (selectedFilter) {
//        "Today" -> exercisesByDateState.isLoading
//        "This Month" -> exercisesByMonthState.isLoading
//        "This Year" -> exercisesByYearState.isLoading
//        "By Type" -> exercisesByTypeState.isLoading
//        "By Intensity" -> exercisesByIntensityState.isLoading
//        else -> allExercisesState.isLoading
//    }
//
//    val error = when (selectedFilter) {
//        "Today" -> exercisesByDateState.error
//        "This Month" -> exercisesByMonthState.error
//        "This Year" -> exercisesByYearState.error
//        "By Type" -> exercisesByTypeState.error
//        "By Intensity" -> exercisesByIntensityState.error
//        else -> allExercisesState.error
//    }
//
//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//                    editingExercise = null
//                    showDialog = true
//                },
//                containerColor = MaterialTheme.colorScheme.tertiary
//            ) {
//                Icon(Icons.Default.Add, contentDescription = "Add Exercise")
//            }
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp)
//        ) {
//            // Header
//            Text(
//                "Exercise Tracker",
//                style = MaterialTheme.typography.headlineMedium,
//                fontWeight = FontWeight.Bold
//            )
//            Spacer(Modifier.height(16.dp))
//
//            // Stats Cards Row
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                // Calories Card
//                Card(
//                    modifier = Modifier.weight(1f),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
//                ) {
//                    Column(
//                        modifier = Modifier.padding(16.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Icon(
//                            Icons.Default.LocalFireDepartment,
//                            contentDescription = null,
//                            tint = MaterialTheme.colorScheme.error,
//                            modifier = Modifier.size(32.dp)
//                        )
//                        Spacer(Modifier.height(8.dp))
//                        if (totalCaloriesState.isLoading) {
//                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
//                        } else {
//                            Text(
//                                "${totalCaloriesState.value.toInt()}",
//                                style = MaterialTheme.typography.headlineMedium,
//                                fontWeight = FontWeight.Bold,
//                                color = MaterialTheme.colorScheme.error
//                            )
//                            Text("kcal Burned", style = MaterialTheme.typography.bodySmall)
//                            Text("Today", style = MaterialTheme.typography.labelSmall)
//                        }
//                    }
//                }
//
//                // Duration Card
//                Card(
//                    modifier = Modifier.weight(1f),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
//                ) {
//                    Column(
//                        modifier = Modifier.padding(16.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Icon(
//                            Icons.Default.Timer,
//                            contentDescription = null,
//                            tint = MaterialTheme.colorScheme.tertiary,
//                            modifier = Modifier.size(32.dp)
//                        )
//                        Spacer(Modifier.height(8.dp))
//                        if (totalDurationState.isLoading) {
//                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
//                        } else {
//                            Text(
//                                "${totalDurationState.value}",
//                                style = MaterialTheme.typography.headlineMedium,
//                                fontWeight = FontWeight.Bold,
//                                color = MaterialTheme.colorScheme.tertiary
//                            )
//                            Text("minutes", style = MaterialTheme.typography.bodySmall)
//                            Text("Today", style = MaterialTheme.typography.labelSmall)
//                        }
//                    }
//                }
//            }
//
//            Spacer(Modifier.height(16.dp))
//
//            // Filter Row
//            LazyColumn {
//                item {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(8.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text("Filter:", fontWeight = FontWeight.Medium)
//
//                        FilterChip(
//                            selected = selectedFilter == "All",
//                            onClick = {
//                                selectedFilter = "All"
//                                viewModel.getAllExercisesDescending()
//                            },
//                            label = { Text("All") }
//                        )
//
//                        FilterChip(
//                            selected = selectedFilter == "Today",
//                            onClick = {
//                                selectedFilter = "Today"
//                                viewModel.getExercisesByDate(selectedDate)
//                            },
//                            label = { Text("Today") }
//                        )
//
//                        FilterChip(
//                            selected = selectedFilter == "This Month",
//                            onClick = {
//                                selectedFilter = "This Month"
//                                viewModel.getExercisesByMonth(selectedMonth, selectedYear)
//                            },
//                            label = { Text("Month") }
//                        )
//                    }
//                }
//
//                item {
//                    Spacer(Modifier.height(8.dp))
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        var showTypeMenu by remember { mutableStateOf(false) }
//                        var showIntensityMenu by remember { mutableStateOf(false) }
//
//                        Box {
//                            FilterChip(
//                                selected = selectedFilter == "By Type",
//                                onClick = { showTypeMenu = true },
//                                label = { Text("Type${if (selectedType.isNotEmpty()) ": $selectedType" else ""}") }
//                            )
//                            DropdownMenu(
//                                expanded = showTypeMenu,
//                                onDismissRequest = { showTypeMenu = false }
//                            ) {
//                                listOf(
//                                    Constants.EXERCISE_CARDIO,
//                                    Constants.EXERCISE_STRENGTH,
//                                    Constants.EXERCISE_FLEXIBILITY,
//                                    Constants.EXERCISE_SPORTS,
//                                    Constants.EXERCISE_YOGA,
//                                    Constants.EXERCISE_HIIT
//                                ).forEach { type ->
//                                    DropdownMenuItem(
//                                        text = { Text(type) },
//                                        onClick = {
//                                            selectedType = type
//                                            selectedFilter = "By Type"
//                                            viewModel.getExercisesByType(type)
//                                            showTypeMenu = false
//                                        }
//                                    )
//                                }
//                            }
//                        }
//
//                        Box {
//                            FilterChip(
//                                selected = selectedFilter == "By Intensity",
//                                onClick = { showIntensityMenu = true },
//                                label = { Text("Intensity${if (selectedIntensity.isNotEmpty()) ": $selectedIntensity" else ""}") }
//                            )
//                            DropdownMenu(
//                                expanded = showIntensityMenu,
//                                onDismissRequest = { showIntensityMenu = false }
//                            ) {
//                                listOf(
//                                    Constants.INTENSITY_LOW,
//                                    Constants.INTENSITY_MEDIUM,
//                                    Constants.INTENSITY_HIGH,
//                                    Constants.INTENSITY_VERY_HIGH
//                                ).forEach { intensity ->
//                                    DropdownMenuItem(
//                                        text = { Text(intensity) },
//                                        onClick = {
//                                            selectedIntensity = intensity
//                                            selectedFilter = "By Intensity"
//                                            viewModel.getExercisesByIntensity(intensity)
//                                            showIntensityMenu = false
//                                        }
//                                    )
//                                }
//                            }
//                        }
//
//                        Spacer(Modifier.weight(1f))
//
//                        IconButton(
//                            onClick = {
//                                when (selectedFilter) {
//                                    "All" -> viewModel.getAllExercisesDescending()
//                                    "Today" -> viewModel.getExercisesByDate(selectedDate)
//                                    "This Month" -> viewModel.getExercisesByMonth(selectedMonth, selectedYear)
//                                    "This Year" -> viewModel.getExercisesByYear(selectedYear)
//                                    "By Type" -> if (selectedType.isNotEmpty()) viewModel.getExercisesByType(selectedType)
//                                    "By Intensity" -> if (selectedIntensity.isNotEmpty()) viewModel.getExercisesByIntensity(selectedIntensity)
//                                }
//                                viewModel.getTotalCaloriesBurnedByDate(selectedDate)
//                                viewModel.getTotalDurationByDate(selectedDate)
//                            }
//                        ) {
//                            Icon(Icons.Default.Refresh, contentDescription = "Refresh")
//                        }
//                    }
//                }
//
//                item {
//                    Spacer(Modifier.height(16.dp))
//                }
//
//                // Content
//                when {
//                    isLoading -> {
//                        item {
//                            Box(Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
//                                CircularProgressIndicator()
//                            }
//                        }
//                    }
//                    error.isNotEmpty() -> {
//                        item {
//                            ErrorView(message = error) {
//                                when (selectedFilter) {
//                                    "All" -> viewModel.getAllExercisesDescending()
//                                    "Today" -> viewModel.getExercisesByDate(selectedDate)
//                                    "This Month" -> viewModel.getExercisesByMonth(selectedMonth, selectedYear)
//                                    "This Year" -> viewModel.getExercisesByYear(selectedYear)
//                                    "By Type" -> if (selectedType.isNotEmpty()) viewModel.getExercisesByType(selectedType)
//                                    "By Intensity" -> if (selectedIntensity.isNotEmpty()) viewModel.getExercisesByIntensity(selectedIntensity)
//                                }
//                            }
//                        }
//                    }
//                    displayData.isEmpty() -> {
//                        item {
//                            EmptyView(message = "No exercises recorded for $selectedFilter")
//                        }
//                    }
//                    else -> {
//                        item {
//                            Text(
//                                "Exercise Records (${displayData.size})",
//                                style = MaterialTheme.typography.titleMedium,
//                                fontWeight = FontWeight.Bold
//                            )
//                            Spacer(Modifier.height(8.dp))
//                        }
//                        items(displayData) { exercise ->
//                            ExerciseItem(
//                                exercise = exercise,
//                                onEdit = {
//                                    editingExercise = exercise
//                                    showDialog = true
//                                },
//                                onDelete = { viewModel.deleteExercise(exercise) }
//                            )
//                            Spacer(Modifier.height(8.dp))
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    if (showDialog) {
//        AddEditExerciseDialog(
//            exercise = editingExercise,
//            onDismiss = { showDialog = false },
//            onSave = { newExercise ->
//                viewModel.upsertExercise(newExercise)
//                showDialog = false
//            }
//        )
//    }
//}
//
//@Composable
//fun ExerciseItem(
//    exercise: Exercise,
//    onEdit: () -> Unit,
//    onDelete: () -> Unit
//) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        shape = MaterialTheme.shapes.medium,
//        elevation = CardDefaults.cardElevation(2.dp)
//    ) {
//        Column(Modifier.padding(16.dp)) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.Top
//            ) {
//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(12.dp),
//                    modifier = Modifier.weight(1f)
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .size(48.dp)
//                            .clip(CircleShape)
//                            .background(MaterialTheme.colorScheme.tertiaryContainer),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            Icons.Default.FitnessCenter,
//                            contentDescription = null,
//                            tint = MaterialTheme.colorScheme.tertiary
//                        )
//                    }
//
//                    Column {
//                        Text(
//                            exercise.exerciseName,
//                            fontWeight = FontWeight.Bold,
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                            Text(
//                                exercise.exerciseType,
//                                style = MaterialTheme.typography.bodySmall,
//                                color = MaterialTheme.colorScheme.primary
//                            )
//                            Text("•", style = MaterialTheme.typography.bodySmall)
//                            Text(
//                                exercise.intensity,
//                                style = MaterialTheme.typography.bodySmall,
//                                color = MaterialTheme.colorScheme.tertiary
//                            )
//                        }
//                        Spacer(Modifier.height(4.dp))
//                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
//                                Icon(
//                                    Icons.Default.Timer,
//                                    contentDescription = null,
//                                    modifier = Modifier.size(16.dp),
//                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//                                )
//                                Text(
//                                    "${exercise.durationMinutes} min",
//                                    style = MaterialTheme.typography.bodySmall
//                                )
//                            }
//                            exercise.caloriesBurned?.let { cal ->
//                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
//                                    Icon(
//                                        Icons.Default.LocalFireDepartment,
//                                        contentDescription = null,
//                                        modifier = Modifier.size(16.dp),
//                                        tint = MaterialTheme.colorScheme.error
//                                    )
//                                    Text(
//                                        "${cal.toInt()} kcal",
//                                        style = MaterialTheme.typography.bodySmall
//                                    )
//                                }
//                            }
//                        }
//                        Text(
//                            "${exercise.date} - ${exercise.timeOfDay}",
//                            style = MaterialTheme.typography.labelSmall,
//                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
//                        )
//
//                        exercise.sets?.let { sets ->
//                            exercise.reps?.let { reps ->
//                                Text(
//                                    "Sets: $sets × $reps reps${exercise.weight?.let { " @ ${it}kg" } ?: ""}",
//                                    style = MaterialTheme.typography.bodySmall
//                                )
//                            }
//                        }
//                        exercise.distance?.let {
//                            Text("Distance: $it km", style = MaterialTheme.typography.bodySmall)
//                        }
//                        exercise.heartRate?.let {
//                            Text("HR: $it bpm", style = MaterialTheme.typography.bodySmall)
//                        }
//                    }
//                }
//
//                Row {
//                    IconButton(onClick = onEdit) {
//                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
//                    }
//                    IconButton(onClick = onDelete) {
//                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun AddEditExerciseDialog(
//    exercise: Exercise?,
//    onDismiss: () -> Unit,
//    onSave: (Exercise) -> Unit
//) {
//    var exerciseName by remember { mutableStateOf(exercise?.exerciseName ?: "") }
//    var exerciseType by remember { mutableStateOf(exercise?.exerciseType ?: Constants.EXERCISE_CARDIO) }
//    var date by remember { mutableStateOf(exercise?.date ?: "") }
//    var timeOfDay by remember { mutableStateOf(exercise?.timeOfDay ?: Constants.MORNING) }
//    var durationMinutes by remember { mutableStateOf(exercise?.durationMinutes?.toString() ?: "") }
//    var caloriesBurned by remember { mutableStateOf(exercise?.caloriesBurned?.toString() ?: "") }
//    var intensity by remember { mutableStateOf(exercise?.intensity ?: Constants.INTENSITY_MEDIUM) }
//    var sets by remember { mutableStateOf(exercise?.sets?.toString() ?: "") }
//    var reps by remember { mutableStateOf(exercise?.reps?.toString() ?: "") }
//    var weight by remember { mutableStateOf(exercise?.weight?.toString() ?: "") }
//    var distance by remember { mutableStateOf(exercise?.distance?.toString() ?: "") }
//    var heartRate by remember { mutableStateOf(exercise?.heartRate?.toString() ?: "") }
//    var notes by remember { mutableStateOf(exercise?.notes ?: "") }
//    var isCompleted by remember { mutableStateOf(exercise?.isCompleted ?: true) }
//
//    val exerciseTypes = listOf(
//        Constants.EXERCISE_CARDIO,
//        Constants.EXERCISE_STRENGTH,
//        Constants.EXERCISE_FLEXIBILITY,
//        Constants.EXERCISE_SPORTS,
//        Constants.EXERCISE_YOGA,
//        Constants.EXERCISE_HIIT
//    )
//    val intensities = listOf(
//        Constants.INTENSITY_LOW,
//        Constants.INTENSITY_MEDIUM,
//        Constants.INTENSITY_HIGH,
//        Constants.INTENSITY_VERY_HIGH
//    )
//    val timesOfDay = listOf(Constants.MORNING, Constants.AFTERNOON, Constants.EVENING, Constants.NIGHT)
//
//    val context = LocalContext.current
//    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
//
//    fun openDatePicker() {
//        val calendar = Calendar.getInstance()
//        DatePickerDialog(
//            context,
//            { _, year, month, day ->
//                val cal = Calendar.getInstance()
//                cal.set(year, month, day)
//                date = dateFormatter.format(cal.time)
//            },
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH)
//        ).show()
//    }
//
//    LaunchedEffect(Unit) {
//        if (exercise == null) {
//            date = dateFormatter.format(Calendar.getInstance().time)
//        }
//    }
//
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = { Text(if (exercise == null) "Add Exercise" else "Edit Exercise") },
//        text = {
//            LazyColumn(
//                modifier = Modifier.fillMaxWidth().height(500.dp),
//                verticalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                item {
//                    OutlinedTextField(
//                        value = exerciseName,
//                        onValueChange = { exerciseName = it },
//                        label = { Text("Exercise Name") },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//
//                item {
//                    DropdownSelector(
//                        label = "Exercise Type",
//                        options = exerciseTypes,
//                        selected = exerciseType,
//                        onSelectedChange = { exerciseType = it }
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = date,
//                        onValueChange = {},
//                        label = { Text("Date") },
//                        readOnly = true,
//                        trailingIcon = {
//                            IconButton(onClick = { openDatePicker() }) {
//                                Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
//                            }
//                        },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//
//                item {
//                    DropdownSelector(
//                        label = "Time of Day",
//                        options = timesOfDay,
//                        selected = timeOfDay,
//                        onSelectedChange = { timeOfDay = it }
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = durationMinutes,
//                        onValueChange = { durationMinutes = it },
//                        label = { Text("Duration (minutes)") },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = caloriesBurned,
//                        onValueChange = { caloriesBurned = it },
//                        label = { Text("Calories Burned (Optional)") },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//
//                item {
//                    DropdownSelector(
//                        label = "Intensity",
//                        options = intensities,
//                        selected = intensity,
//                        onSelectedChange = { intensity = it }
//                    )
//                }
//
//                item {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        OutlinedTextField(
//                            value = sets,
//                            onValueChange = { sets = it },
//                            label = { Text("Sets") },
//                            modifier = Modifier.weight(1f)
//                        )
//                        OutlinedTextField(
//                            value = reps,
//                            onValueChange = { reps = it },
//                            label = { Text("Reps") },
//                            modifier = Modifier.weight(1f)
//                        )
//                    }
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = weight,
//                        onValueChange = { weight = it },
//                        label = { Text("Weight (kg)") },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = distance,
//                        onValueChange = { distance = it },
//                        label = { Text("Distance (km)") },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = heartRate,
//                        onValueChange = { heartRate = it },
//                        label = { Text("Heart Rate (bpm)") },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//
//                item {
//                    OutlinedTextField(
//                        value = notes,
//                        onValueChange = { notes = it },
//                        label = { Text("Notes") },
//                        modifier = Modifier.fillMaxWidth(),
//                        maxLines = 3
//                    )
//                }
//
//                item {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Checkbox(
//                            checked = isCompleted,
//                            onCheckedChange = { isCompleted = it }
//                        )
//                        Text("Workout Completed")
//                    }
//                }
//            }
//        },
//        confirmButton = {
//            Button(
//                onClick = {
//                    if (exerciseName.isNotBlank() && date.isNotBlank() && durationMinutes.isNotBlank()) {
//                        val calendar = Calendar.getInstance()
//                        calendar.time = dateFormatter.parse(date)!!
//                        onSave(
//                            Exercise(
//                                id = exercise?.id ?: 0,
//                                exerciseName = exerciseName,
//                                exerciseType = exerciseType,
//                                date = date,
//                                timeOfDay = timeOfDay,
//                                durationMinutes = durationMinutes.toIntOrNull() ?: 0,
//                                caloriesBurned = caloriesBurned.toDoubleOrNull(),
//                                intensity = intensity,
//                                sets = sets.toIntOrNull(),
//                                reps = reps.toIntOrNull(),
//                                weight = weight.toDoubleOrNull(),
//                                distance = distance.toDoubleOrNull(),
//                                heartRate = heartRate.toIntOrNull(),
//                                month = calendar.get(Calendar.MONTH) + 1,
//                                year = calendar.get(Calendar.YEAR),
//                                notes = notes.ifBlank { null },
//                                isCompleted = isCompleted
//                            )
//                        )
//                    }
//                }
//            ) {
//                Text("Save")
//            }
//        },
//        dismissButton = {
//            OutlinedButton(onClick = onDismiss) { Text("Cancel") }
//        }
//    )
//}

package com.scrymz.bitebuddy.presentation.screens

import android.app.DatePickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.scrymz.bitebuddy.Constants.Constants
import com.scrymz.bitebuddy.data.entity.Exercise
import com.scrymz.bitebuddy.presentation.viewmodels.ExerciseViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun ExerciseScreen(
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    val allExercisesState by viewModel.allExercisesState.collectAsState()
    val exercisesByDateState by viewModel.exercisesByDateState.collectAsState()
    val exercisesByMonthState by viewModel.exercisesByMonthState.collectAsState()
    val exercisesByYearState by viewModel.exercisesByYearState.collectAsState()
    val exercisesByTypeState by viewModel.exercisesByTypeState.collectAsState()
    val exercisesByIntensityState by viewModel.exercisesByIntensityState.collectAsState()
    val upsertState by viewModel.upsertExerciseState.collectAsState()
    val deleteState by viewModel.deleteExerciseState.collectAsState()
    val totalCaloriesState by viewModel.totalCaloriesBurnedState.collectAsState()
    val totalDurationState by viewModel.totalDurationState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var editingExercise by remember { mutableStateOf<Exercise?>(null) }
    var selectedFilter by remember { mutableStateOf("All") }
    var selectedDate by remember { mutableStateOf("") }
    var selectedMonth by remember { mutableStateOf(0) }
    var selectedYear by remember { mutableStateOf(0) }
    var selectedType by remember { mutableStateOf("") }
    var selectedIntensity by remember { mutableStateOf("") }

    // Live Timer States
    var isTimerRunning by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }
    var elapsedSeconds by remember { mutableIntStateOf(0) }
    var showTimerDialog by remember { mutableStateOf(false) }
    var showStopConfirmDialog by remember { mutableStateOf(false) }

    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val calendar = Calendar.getInstance()

    // Initialize with today's date
    LaunchedEffect(Unit) {
        val today = dateFormatter.format(calendar.time)
        selectedDate = today
        selectedMonth = calendar.get(Calendar.MONTH) + 1
        selectedYear = calendar.get(Calendar.YEAR)

        viewModel.getAllExercisesDescending()
        viewModel.getTotalCaloriesBurnedByDate(today)
        viewModel.getTotalDurationByDate(today)
    }

    // Timer Effect
    LaunchedEffect(isTimerRunning, isPaused) {
        if (isTimerRunning && !isPaused) {
            while (true) {
                delay(1000L)
                elapsedSeconds++
            }
        }
    }

    // Real-time update after operations
    LaunchedEffect(upsertState.message, deleteState.message) {
        if (upsertState.message.isNotEmpty() || deleteState.message.isNotEmpty()) {
            when (selectedFilter) {
                "All" -> viewModel.getAllExercisesDescending()
                "Today" -> viewModel.getExercisesByDate(selectedDate)
                "This Month" -> viewModel.getExercisesByMonth(selectedMonth, selectedYear)
                "This Year" -> viewModel.getExercisesByYear(selectedYear)
                "By Type" -> if (selectedType.isNotEmpty()) viewModel.getExercisesByType(selectedType)
                "By Intensity" -> if (selectedIntensity.isNotEmpty()) viewModel.getExercisesByIntensity(selectedIntensity)
            }
            viewModel.getTotalCaloriesBurnedByDate(selectedDate)
            viewModel.getTotalDurationByDate(selectedDate)
        }
    }

    val displayData = when (selectedFilter) {
        "Today" -> exercisesByDateState.data
        "This Month" -> exercisesByMonthState.data
        "This Year" -> exercisesByYearState.data
        "By Type" -> exercisesByTypeState.data
        "By Intensity" -> exercisesByIntensityState.data
        else -> allExercisesState.data
    }

    val isLoading = when (selectedFilter) {
        "Today" -> exercisesByDateState.isLoading
        "This Month" -> exercisesByMonthState.isLoading
        "This Year" -> exercisesByYearState.isLoading
        "By Type" -> exercisesByTypeState.isLoading
        "By Intensity" -> exercisesByIntensityState.isLoading
        else -> allExercisesState.isLoading
    }

    val error = when (selectedFilter) {
        "Today" -> exercisesByDateState.error
        "This Month" -> exercisesByMonthState.error
        "This Year" -> exercisesByYearState.error
        "By Type" -> exercisesByTypeState.error
        "By Intensity" -> exercisesByIntensityState.error
        else -> allExercisesState.error
    }

    Scaffold(
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Start Timer Button
                if (!isTimerRunning) {
                    SmallFloatingActionButton(
                        onClick = {
                            showTimerDialog = true
                        },
                        containerColor = MaterialTheme.colorScheme.secondary
                    ) {
                        Icon(Icons.Default.Timer, contentDescription = "Start Timer")
                    }
                }

                // Add Exercise Button
                FloatingActionButton(
                    onClick = {
                        editingExercise = null
                        showDialog = true
                    },
                    containerColor = MaterialTheme.colorScheme.tertiary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Exercise")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Live Timer Display
            AnimatedVisibility(
                visible = isTimerRunning,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                LiveTimerCard(
                    elapsedSeconds = elapsedSeconds,
                    isPaused = isPaused,
                    onPauseResume = { isPaused = !isPaused },
                    onStop = { showStopConfirmDialog = true }
                )
                Spacer(Modifier.height(16.dp))
            }

            // Header
            Text(
                "Exercise Tracker",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))

            // Stats Cards Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Calories Card
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        if (totalCaloriesState.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        } else {
                            Text(
                                "${totalCaloriesState.value.toInt()}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.error
                            )
                            Text("kcal Burned", style = MaterialTheme.typography.bodySmall)
                            Text("Today", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }

                // Duration Card
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Timer,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        if (totalDurationState.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        } else {
                            Text(
                                "${totalDurationState.value}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            Text("minutes", style = MaterialTheme.typography.bodySmall)
                            Text("Today", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Filter Row
            LazyColumn {
                item {
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
                                viewModel.getAllExercisesDescending()
                            },
                            label = { Text("All") }
                        )

                        FilterChip(
                            selected = selectedFilter == "Today",
                            onClick = {
                                selectedFilter = "Today"
                                viewModel.getExercisesByDate(selectedDate)
                            },
                            label = { Text("Today") }
                        )

                        FilterChip(
                            selected = selectedFilter == "This Month",
                            onClick = {
                                selectedFilter = "This Month"
                                viewModel.getExercisesByMonth(selectedMonth, selectedYear)
                            },
                            label = { Text("Month") }
                        )
                    }
                }

                item {
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        var showTypeMenu by remember { mutableStateOf(false) }
                        var showIntensityMenu by remember { mutableStateOf(false) }

                        Box {
                            FilterChip(
                                selected = selectedFilter == "By Type",
                                onClick = { showTypeMenu = true },
                                label = { Text("Type${if (selectedType.isNotEmpty()) ": $selectedType" else ""}") }
                            )
                            DropdownMenu(
                                expanded = showTypeMenu,
                                onDismissRequest = { showTypeMenu = false }
                            ) {
                                listOf(
                                    Constants.EXERCISE_CARDIO,
                                    Constants.EXERCISE_STRENGTH,
                                    Constants.EXERCISE_FLEXIBILITY,
                                    Constants.EXERCISE_SPORTS,
                                    Constants.EXERCISE_YOGA,
                                    Constants.EXERCISE_HIIT
                                ).forEach { type ->
                                    DropdownMenuItem(
                                        text = { Text(type) },
                                        onClick = {
                                            selectedType = type
                                            selectedFilter = "By Type"
                                            viewModel.getExercisesByType(type)
                                            showTypeMenu = false
                                        }
                                    )
                                }
                            }
                        }

                        Box {
                            FilterChip(
                                selected = selectedFilter == "By Intensity",
                                onClick = { showIntensityMenu = true },
                                label = { Text("Intensity${if (selectedIntensity.isNotEmpty()) ": $selectedIntensity" else ""}") }
                            )
                            DropdownMenu(
                                expanded = showIntensityMenu,
                                onDismissRequest = { showIntensityMenu = false }
                            ) {
                                listOf(
                                    Constants.INTENSITY_LOW,
                                    Constants.INTENSITY_MEDIUM,
                                    Constants.INTENSITY_HIGH,
                                    Constants.INTENSITY_VERY_HIGH
                                ).forEach { intensity ->
                                    DropdownMenuItem(
                                        text = { Text(intensity) },
                                        onClick = {
                                            selectedIntensity = intensity
                                            selectedFilter = "By Intensity"
                                            viewModel.getExercisesByIntensity(intensity)
                                            showIntensityMenu = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.weight(1f))

                        IconButton(
                            onClick = {
                                when (selectedFilter) {
                                    "All" -> viewModel.getAllExercisesDescending()
                                    "Today" -> viewModel.getExercisesByDate(selectedDate)
                                    "This Month" -> viewModel.getExercisesByMonth(selectedMonth, selectedYear)
                                    "This Year" -> viewModel.getExercisesByYear(selectedYear)
                                    "By Type" -> if (selectedType.isNotEmpty()) viewModel.getExercisesByType(selectedType)
                                    "By Intensity" -> if (selectedIntensity.isNotEmpty()) viewModel.getExercisesByIntensity(selectedIntensity)
                                }
                                viewModel.getTotalCaloriesBurnedByDate(selectedDate)
                                viewModel.getTotalDurationByDate(selectedDate)
                            }
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                        }
                    }
                }

                item {
                    Spacer(Modifier.height(16.dp))
                }

                // Content
                when {
                    isLoading -> {
                        item {
                            Box(Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    error.isNotEmpty() -> {
                        item {
                            ErrorView(message = error) {
                                when (selectedFilter) {
                                    "All" -> viewModel.getAllExercisesDescending()
                                    "Today" -> viewModel.getExercisesByDate(selectedDate)
                                    "This Month" -> viewModel.getExercisesByMonth(selectedMonth, selectedYear)
                                    "This Year" -> viewModel.getExercisesByYear(selectedYear)
                                    "By Type" -> if (selectedType.isNotEmpty()) viewModel.getExercisesByType(selectedType)
                                    "By Intensity" -> if (selectedIntensity.isNotEmpty()) viewModel.getExercisesByIntensity(selectedIntensity)
                                }
                            }
                        }
                    }
                    displayData.isEmpty() -> {
                        item {
                            EmptyView(message = "No exercises recorded for $selectedFilter")
                        }
                    }
                    else -> {
                        item {
                            Text(
                                "Exercise Records (${displayData.size})",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(8.dp))
                        }
                        items(displayData) { exercise ->
                            ExerciseItem(
                                exercise = exercise,
                                onEdit = {
                                    editingExercise = exercise
                                    showDialog = true
                                },
                                onDelete = { viewModel.deleteExercise(exercise) }
                            )
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }

    // Start Timer Dialog
    if (showTimerDialog) {
        StartTimerDialog(
            onDismiss = { showTimerDialog = false },
            onStart = {
                isTimerRunning = true
                isPaused = false
                elapsedSeconds = 0
                showTimerDialog = false
            }
        )
    }

    // Stop Confirmation Dialog
    if (showStopConfirmDialog) {
        StopTimerDialog(
            elapsedSeconds = elapsedSeconds,
            onDismiss = { showStopConfirmDialog = false },
            onSave = { exercise ->
                viewModel.upsertExercise(exercise)
                isTimerRunning = false
                isPaused = false
                elapsedSeconds = 0
                showStopConfirmDialog = false
            },
            onDiscard = {
                isTimerRunning = false
                isPaused = false
                elapsedSeconds = 0
                showStopConfirmDialog = false
            }
        )
    }

    if (showDialog) {
        AddEditExerciseDialog(
            exercise = editingExercise,
            onDismiss = { showDialog = false },
            onSave = { newExercise ->
                viewModel.upsertExercise(newExercise)
                showDialog = false
            }
        )
    }
}

@Composable
fun LiveTimerCard(
    elapsedSeconds: Int,
    isPaused: Boolean,
    onPauseResume: () -> Unit,
    onStop: () -> Unit
) {
    val hours = elapsedSeconds / 3600
    val minutes = (elapsedSeconds % 3600) / 60
    val seconds = elapsedSeconds % 60

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isPaused) MaterialTheme.colorScheme.errorContainer
            else MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Timer,
                    contentDescription = null,
                    tint = if (isPaused) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    if (isPaused) "PAUSED" else "LIVE WORKOUT",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isPaused) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                String.format("%02d:%02d:%02d", hours, minutes, seconds),
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = if (isPaused) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = onPauseResume,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        if (isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                        contentDescription = if (isPaused) "Resume" else "Pause"
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(if (isPaused) "Resume" else "Pause")
                }

                OutlinedButton(
                    onClick = onStop,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Stop, contentDescription = "Stop")
                    Spacer(Modifier.width(8.dp))
                    Text("Stop")
                }
            }
        }
    }
}

@Composable
fun StartTimerDialog(
    onDismiss: () -> Unit,
    onStart: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Start Live Workout") },
        text = {
            Column {
                Text("Ready to track your workout in real-time?")
                Spacer(Modifier.height(8.dp))
                Text(
                    "• Timer will start immediately\n• You can pause/resume anytime\n• Save your workout when done",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        confirmButton = {
            Button(onClick = onStart) {
                Text("Start Now")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun StopTimerDialog(
    elapsedSeconds: Int,
    onDismiss: () -> Unit,
    onSave: (Exercise) -> Unit,
    onDiscard: () -> Unit
) {
    var exerciseName by remember { mutableStateOf("") }
    var exerciseType by remember { mutableStateOf(Constants.EXERCISE_CARDIO) }
    var intensity by remember { mutableStateOf(Constants.INTENSITY_MEDIUM) }
    var caloriesBurned by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val exerciseTypes = listOf(
        Constants.EXERCISE_CARDIO,
        Constants.EXERCISE_STRENGTH,
        Constants.EXERCISE_FLEXIBILITY,
        Constants.EXERCISE_SPORTS,
        Constants.EXERCISE_YOGA,
        Constants.EXERCISE_HIIT
    )
    val intensities = listOf(
        Constants.INTENSITY_LOW,
        Constants.INTENSITY_MEDIUM,
        Constants.INTENSITY_HIGH,
        Constants.INTENSITY_VERY_HIGH
    )

    val durationMinutes = elapsedSeconds / 60
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val today = dateFormatter.format(Calendar.getInstance().time)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Save Workout") },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().height(400.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Duration: $durationMinutes minutes",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                item {
                    OutlinedTextField(
                        value = exerciseName,
                        onValueChange = { exerciseName = it },
                        label = { Text("Exercise Name *") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    DropdownSelector(
                        label = "Exercise Type",
                        options = exerciseTypes,
                        selected = exerciseType,
                        onSelectedChange = { exerciseType = it }
                    )
                }

                item {
                    DropdownSelector(
                        label = "Intensity",
                        options = intensities,
                        selected = intensity,
                        onSelectedChange = { intensity = it }
                    )
                }

                item {
                    OutlinedTextField(
                        value = caloriesBurned,
                        onValueChange = { caloriesBurned = it },
                        label = { Text("Calories Burned (Optional)") },
                        modifier = Modifier.fillMaxWidth()
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
                    if (exerciseName.isNotBlank()) {
                        val calendar = Calendar.getInstance()
                        onSave(
                            Exercise(
                                exerciseName = exerciseName,
                                exerciseType = exerciseType,
                                durationMinutes = durationMinutes,
                                caloriesBurned = caloriesBurned.toDoubleOrNull(),
                                intensity = intensity,
                                date = today,
                                timeOfDay = when (calendar.get(Calendar.HOUR_OF_DAY)) {
                                    in 5..11 -> Constants.MORNING
                                    in 12..16 -> Constants.AFTERNOON
                                    in 17..20 -> Constants.EVENING
                                    else -> Constants.NIGHT
                                },
                                month = calendar.get(Calendar.MONTH) + 1,
                                year = calendar.get(Calendar.YEAR),
                                notes = notes.ifBlank { null },
                                isCompleted = true
                            )
                        )
                    }
                },
                enabled = exerciseName.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = onDiscard) {
                    Text("Discard")
                }
                OutlinedButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        }
    )
}

@Composable
fun ExerciseItem(
    exercise: Exercise,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.FitnessCenter,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    Column {
                        Text(
                            exercise.exerciseName,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                exercise.exerciseType,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text("•", style = MaterialTheme.typography.bodySmall)
                            Text(
                                exercise.intensity,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(
                                    Icons.Default.Timer,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                                Text(
                                    "${exercise.durationMinutes} min",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            exercise.caloriesBurned?.let { cal ->
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(
                                        Icons.Default.LocalFireDepartment,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                    Text(
                                        "${cal.toInt()} kcal",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                        Text(
                            "${exercise.date} - ${exercise.timeOfDay}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )

                        exercise.sets?.let { sets ->
                            exercise.reps?.let { reps ->
                                Text(
                                    "Sets: $sets × $reps reps${exercise.weight?.let { " @ ${it}kg" } ?: ""}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                        exercise.distance?.let {
                            Text("Distance: $it km", style = MaterialTheme.typography.bodySmall)
                        }
                        exercise.heartRate?.let {
                            Text("HR: $it bpm", style = MaterialTheme.typography.bodySmall)
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
}

@Composable
fun AddEditExerciseDialog(
    exercise: Exercise?,
    onDismiss: () -> Unit,
    onSave: (Exercise) -> Unit
) {
    var exerciseName by remember { mutableStateOf(exercise?.exerciseName ?: "") }
    var exerciseType by remember { mutableStateOf(exercise?.exerciseType ?: Constants.EXERCISE_CARDIO) }
    var date by remember { mutableStateOf(exercise?.date ?: "") }
    var timeOfDay by remember { mutableStateOf(exercise?.timeOfDay ?: Constants.MORNING) }
    var durationMinutes by remember { mutableStateOf(exercise?.durationMinutes?.toString() ?: "") }
    var caloriesBurned by remember { mutableStateOf(exercise?.caloriesBurned?.toString() ?: "") }
    var intensity by remember { mutableStateOf(exercise?.intensity ?: Constants.INTENSITY_MEDIUM) }
    var sets by remember { mutableStateOf(exercise?.sets?.toString() ?: "") }
    var reps by remember { mutableStateOf(exercise?.reps?.toString() ?: "") }
    var weight by remember { mutableStateOf(exercise?.weight?.toString() ?: "") }
    var distance by remember { mutableStateOf(exercise?.distance?.toString() ?: "") }
    var heartRate by remember { mutableStateOf(exercise?.heartRate?.toString() ?: "") }
    var notes by remember { mutableStateOf(exercise?.notes ?: "") }
    var isCompleted by remember { mutableStateOf(exercise?.isCompleted ?: true) }

    val exerciseTypes = listOf(
        Constants.EXERCISE_CARDIO,
        Constants.EXERCISE_STRENGTH,
        Constants.EXERCISE_FLEXIBILITY,
        Constants.EXERCISE_SPORTS,
        Constants.EXERCISE_YOGA,
        Constants.EXERCISE_HIIT
    )
    val intensities = listOf(
        Constants.INTENSITY_LOW,
        Constants.INTENSITY_MEDIUM,
        Constants.INTENSITY_HIGH,
        Constants.INTENSITY_VERY_HIGH
    )
    val timesOfDay = listOf(Constants.MORNING, Constants.AFTERNOON, Constants.EVENING, Constants.NIGHT)

    val context = LocalContext.current
    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

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

    LaunchedEffect(Unit) {
        if (exercise == null) {
            date = dateFormatter.format(Calendar.getInstance().time)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (exercise == null) "Add Exercise" else "Edit Exercise") },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().height(500.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = exerciseName,
                        onValueChange = { exerciseName = it },
                        label = { Text("Exercise Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    DropdownSelector(
                        label = "Exercise Type",
                        options = exerciseTypes,
                        selected = exerciseType,
                        onSelectedChange = { exerciseType = it }
                    )
                }

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
                    DropdownSelector(
                        label = "Time of Day",
                        options = timesOfDay,
                        selected = timeOfDay,
                        onSelectedChange = { timeOfDay = it }
                    )
                }

                item {
                    OutlinedTextField(
                        value = durationMinutes,
                        onValueChange = { durationMinutes = it },
                        label = { Text("Duration (minutes)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = caloriesBurned,
                        onValueChange = { caloriesBurned = it },
                        label = { Text("Calories Burned (Optional)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    DropdownSelector(
                        label = "Intensity",
                        options = intensities,
                        selected = intensity,
                        onSelectedChange = { intensity = it }
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = sets,
                            onValueChange = { sets = it },
                            label = { Text("Sets") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = reps,
                            onValueChange = { reps = it },
                            label = { Text("Reps") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                item {
                    OutlinedTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        label = { Text("Weight (kg)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = distance,
                        onValueChange = { distance = it },
                        label = { Text("Distance (km)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = heartRate,
                        onValueChange = { heartRate = it },
                        label = { Text("Heart Rate (bpm)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Notes") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isCompleted,
                            onCheckedChange = { isCompleted = it }
                        )
                        Text("Workout Completed")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (exerciseName.isNotBlank() && date.isNotBlank() && durationMinutes.isNotBlank()) {
                        val calendar = Calendar.getInstance()
                        calendar.time = dateFormatter.parse(date)!!
                        onSave(
                            Exercise(
                                id = exercise?.id ?: 0,
                                exerciseName = exerciseName,
                                exerciseType = exerciseType,
                                date = date,
                                timeOfDay = timeOfDay,
                                durationMinutes = durationMinutes.toIntOrNull() ?: 0,
                                caloriesBurned = caloriesBurned.toDoubleOrNull(),
                                intensity = intensity,
                                sets = sets.toIntOrNull(),
                                reps = reps.toIntOrNull(),
                                weight = weight.toDoubleOrNull(),
                                distance = distance.toDoubleOrNull(),
                                heartRate = heartRate.toIntOrNull(),
                                month = calendar.get(Calendar.MONTH) + 1,
                                year = calendar.get(Calendar.YEAR),
                                notes = notes.ifBlank { null },
                                isCompleted = isCompleted
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
