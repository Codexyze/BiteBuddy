package com.scrymz.bitebuddy.presentation.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.scrymz.bitebuddy.presentation.viewmodels.FoodViewModel
import kotlin.math.min

data class NutrientInfo(
    val name: String,
    val unit: String,
    val dailyValue: Double,
    val consumed: Double,
    val description: String,
    val benefits: List<String>,
    val sources: List<String>,
    val deficiencySymptoms: List<String>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MicroDetailsScreen(
    navController: NavController,
    viewModel: FoodViewModel = hiltViewModel()
) {
    val getByDateState by viewModel.getByDateState.collectAsState()
    var selectedDate by remember { mutableStateOf(getTodayDate()) }

    // Load data for selected date
    LaunchedEffect(selectedDate) {
        viewModel.getByDate(selectedDate)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Micronutrient Analysis", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Date Selection
            DateSelectionCard(
                selectedDate = selectedDate,
                onDateChange = { selectedDate = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                getByDateState.isLoading -> {
                    LoadingView()
                }
                getByDateState.error.isNotEmpty() -> {
                    ErrorView(getByDateState.error)
                }
                getByDateState.data.isEmpty() -> {
                    EmptyDataView()
                }
                else -> {
                    val nutrients = calculateNutrients(getByDateState.data)
                    NutrientAnalysisContent(nutrients)
                }
            }
        }
    }
}

@Composable
fun DateSelectionCard(
    selectedDate: String,
    onDateChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.CalendarToday,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                value = selectedDate,
                onValueChange = onDateChange,
                label = { Text("Analysis Date (yyyy-MM-dd)") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }
    }
}

@Composable
fun NutrientAnalysisContent(nutrients: List<NutrientInfo>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Daily Vitamin & Mineral Analysis",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Compare your consumption against recommended daily values",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(nutrients.size) { index ->
            NutrientCard(nutrients[index])
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            HealthRecommendationCard()
        }
    }
}

@Composable
fun NutrientCard(nutrient: NutrientInfo) {
    val progressPercentage = (nutrient.consumed / nutrient.dailyValue).coerceAtMost(1.0)
    val isDeficient = nutrient.consumed < (nutrient.dailyValue * 0.5)
    val isOptimal = nutrient.consumed >= (nutrient.dailyValue * 0.8) && nutrient.consumed <= (nutrient.dailyValue * 1.2)
    val isExcess = nutrient.consumed > (nutrient.dailyValue * 1.5)

    val statusColor = when {
        isExcess -> Color.Red
        isDeficient -> Color.Blue
        isOptimal -> Color.Green
        else -> MaterialTheme.colorScheme.primary
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = nutrient.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "${nutrient.consumed.format()} / ${nutrient.dailyValue.format()} ${nutrient.unit}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = statusColor,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Progress Bar
            Column {
                LinearProgressIndicator(
                    progress = progressPercentage.toFloat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = statusColor,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    strokeCap = StrokeCap.Round
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "0",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "${(progressPercentage * 100).format()}%",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                        color = statusColor
                    )
                    Text(
                        text = "${nutrient.dailyValue.format()} ${nutrient.unit}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Status Badge
            StatusBadge(
                isDeficient = isDeficient,
                isOptimal = isOptimal,
                isExcess = isExcess
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Description
            Text(
                text = nutrient.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )

            if (nutrient.benefits.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                InfoSection(
                    title = "Benefits:",
                    items = nutrient.benefits,
                    color = Color.Green
                )
            }

            if (nutrient.sources.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                InfoSection(
                    title = "Good Sources:",
                    items = nutrient.sources,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            if (isDeficient && nutrient.deficiencySymptoms.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                InfoSection(
                    title = "Deficiency Signs:",
                    items = nutrient.deficiencySymptoms,
                    color = Color.Blue
                )
            }
        }
    }
}

@Composable
fun StatusBadge(
    isDeficient: Boolean,
    isOptimal: Boolean,
    isExcess: Boolean
) {
    val (text, color, backgroundColor) = when {
        isExcess -> Triple("EXCESSIVE", Color.White, Color.Red)
        isDeficient -> Triple("DEFICIENT", Color.Black, Color.Blue)
        isOptimal -> Triple("OPTIMAL", Color.White, Color.Green)
        else -> Triple("MODERATE", Color.Black, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = color,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun InfoSection(
    title: String,
    items: List<String>,
    color: Color
) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
        color = color
    )
    items.take(3).forEach { item ->
        Text(
            text = "• $item",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun HealthRecommendationCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Health Recommendations",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "• Aim for 80-120% of daily values for optimal health\n" +
                        "• Consistently low levels may require dietary adjustments\n" +
                        "• Excessive levels could indicate over-supplementation\n" +
                        "• Consult healthcare providers for personalized advice",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyDataView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.Info,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No food data for this date",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Add some food entries to see micronutrient analysis",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

fun calculateNutrients(foodList: List<com.scrymz.bitebuddy.data.entity.FoodTable>): List<NutrientInfo> {
    val totalCalcium = foodList.sumOf { it.calcium ?: 0.0 }
    val totalIron = foodList.sumOf { it.iron ?: 0.0 }
    val totalMagnesium = foodList.sumOf { it.magnesium ?: 0.0 }
    val totalVitA = foodList.sumOf { it.vitA ?: 0.0 }
    val totalVitB12 = foodList.sumOf { it.vitB12 ?: 0.0 }
    val totalVitC = foodList.sumOf { it.vitC ?: 0.0 }
    val totalVitD = foodList.sumOf { it.vitD ?: 0.0 }

    return listOf(
        NutrientInfo(
            name = "Vitamin A",
            unit = "μg",
            dailyValue = 900.0, // RDA for adults
            consumed = totalVitA,
            description = "Essential for vision, immune function, and cellular growth.",
            benefits = listOf("Night vision", "Immune support", "Healthy skin", "Cell growth"),
            sources = listOf("Carrots", "Sweet potatoes", "Spinach", "Liver", "Eggs"),
            deficiencySymptoms = listOf("Night blindness", "Dry eyes", "Frequent infections")
        ),
        NutrientInfo(
            name = "Vitamin B12",
            unit = "μg",
            dailyValue = 2.4,
            consumed = totalVitB12,
            description = "Crucial for nerve function, DNA synthesis, and red blood cell formation.",
            benefits = listOf("Nerve health", "Energy production", "Brain function", "DNA synthesis"),
            sources = listOf("Meat", "Fish", "Dairy products", "Fortified cereals"),
            deficiencySymptoms = listOf("Fatigue", "Weakness", "Memory problems", "Anemia")
        ),
        NutrientInfo(
            name = "Vitamin C",
            unit = "mg",
            dailyValue = 90.0,
            consumed = totalVitC,
            description = "Powerful antioxidant supporting immune system and collagen synthesis.",
            benefits = listOf("Immune support", "Antioxidant protection", "Iron absorption", "Collagen synthesis"),
            sources = listOf("Citrus fruits", "Berries", "Bell peppers", "Broccoli"),
            deficiencySymptoms = listOf("Scurvy", "Slow wound healing", "Weakened immunity")
        ),
        NutrientInfo(
            name = "Vitamin D",
            unit = "μg",
            dailyValue = 20.0,
            consumed = totalVitD,
            description = "Essential for bone health and calcium absorption.",
            benefits = listOf("Bone health", "Calcium absorption", "Immune function", "Muscle strength"),
            sources = listOf("Sunlight", "Fatty fish", "Fortified milk", "Egg yolks"),
            deficiencySymptoms = listOf("Bone pain", "Muscle weakness", "Increased fracture risk")
        ),
        NutrientInfo(
            name = "Calcium",
            unit = "mg",
            dailyValue = 1000.0,
            consumed = totalCalcium,
            description = "Critical for bone and teeth health, muscle function, and nerve signaling.",
            benefits = listOf("Strong bones", "Teeth health", "Muscle contraction", "Blood clotting"),
            sources = listOf("Dairy products", "Leafy greens", "Sardines", "Almonds"),
            deficiencySymptoms = listOf("Osteoporosis", "Dental problems", "Muscle cramps")
        ),
        NutrientInfo(
            name = "Iron",
            unit = "mg",
            dailyValue = 18.0,
            consumed = totalIron,
            description = "Essential for oxygen transport and energy production.",
            benefits = listOf("Oxygen transport", "Energy production", "Immune function", "Brain development"),
            sources = listOf("Red meat", "Spinach", "Lentils", "Dark chocolate"),
            deficiencySymptoms = listOf("Anemia", "Fatigue", "Pale skin", "Cold hands/feet")
        ),
        NutrientInfo(
            name = "Magnesium",
            unit = "mg",
            dailyValue = 400.0,
            consumed = totalMagnesium,
            description = "Involved in over 300 enzymatic reactions in the body.",
            benefits = listOf("Muscle function", "Heart health", "Bone strength", "Energy metabolism"),
            sources = listOf("Nuts", "Seeds", "Whole grains", "Dark leafy greens"),
            deficiencySymptoms = listOf("Muscle cramps", "Irregular heartbeat", "Seizures")
        )
    )
}