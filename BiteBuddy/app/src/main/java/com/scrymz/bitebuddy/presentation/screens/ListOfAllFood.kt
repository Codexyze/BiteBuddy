package com.scrymz.bitebuddy.presentation.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.scrymz.bitebuddy.data.local.Food
import com.scrymz.bitebuddy.presentation.viewmodels.DatabaseOpnerViewModel

@Composable
fun ListOfAllFood(
    databaseOpenerViewModel: DatabaseOpnerViewModel = hiltViewModel()
) {
    val copyDatabaseState by databaseOpenerViewModel.copyDatabase.collectAsState()
    val allFoodState by databaseOpenerViewModel.getAllDataFromDatabase.collectAsState()

    // 🔹 Start DB copy when screen is first launched
    LaunchedEffect(Unit) {
        Log.d("UI", "Launching copyDatabase() from Composable")
        databaseOpenerViewModel.copyDatabase()
    }

    // 🔹 Once DB copy finishes successfully, trigger data fetch
    LaunchedEffect(copyDatabaseState) {
        Log.d("UI", "copyDatabaseState changed: $copyDatabaseState")
        if (!copyDatabaseState.isLoading && copyDatabaseState.error.isNullOrEmpty()) {
            Log.d("UI", "Triggering getAllDataFromDatabase() since DB copy is done")
            databaseOpenerViewModel.getAllDataFromDatabase()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            // 🔹 Show loader while either copying DB OR fetching data
            copyDatabaseState.isLoading || allFoodState.isLoading -> {
                Log.d("UI", "Showing loading indicator")
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }

            // 🔹 Show DB copy error ONLY if it’s non-empty
            !copyDatabaseState.error.isNullOrEmpty() -> {
                Log.e("UI", "Error copying DB: ${copyDatabaseState.error}")
                Text(
                    text = copyDatabaseState.error ?: "Unknown Error",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )
            }

            // 🔹 Show data fetch error ONLY if it’s non-empty
            !allFoodState.error.isNullOrEmpty() -> {
                Log.e("UI", "Error fetching data: ${allFoodState.error}")
                Text(
                    text = allFoodState.error ?: "Unknown Error",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )
            }

            // 🔹 If we got data, display it
            !allFoodState.data.isNullOrEmpty() -> {
                Log.d("UI", "Rendering LazyColumn with ${allFoodState.data!!.size} items")
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(allFoodState.data!!) { food ->
                        FoodCard(food)
                    }
                }
            }

            // 🔹 If we reach here, DB copy succeeded but no data was found
            else -> {
                Log.w("UI", "No data found to display")
                Text(
                    text = "No Food Data Found",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun FoodCard(food: Food) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // ✅ In future: navigate to a detail screen with food.id
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = food.foodname,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Type: ${food.type ?: "Unknown"}")
            Text(text = "Calories: ${food.calories ?: "N/A"}")
            Text(text = "Protein: ${food.protein ?: "N/A"} g")
        }
    }
}
