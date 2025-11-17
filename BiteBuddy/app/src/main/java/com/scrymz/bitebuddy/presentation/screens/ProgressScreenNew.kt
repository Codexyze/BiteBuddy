package com.scrymz.bitebuddy.presentation.screens

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.scrymz.bitebuddy.data.entity.ImageToProgress
import com.scrymz.bitebuddy.presentation.viewmodels.ImageToProgressViewModel
import com.scrymz.bitebuddy.presentation.viewmodels.ImageViewModel
import com.scrymz.bitebuddy.presentation.utils.InterstitialAdHelper
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ProgressScreen(
    viewModel: ImageViewModel = hiltViewModel(),
    navController: androidx.navigation.NavController
) {
    var showImageMapper by remember { mutableStateOf(false) }
    var showProgressTracker by remember { mutableStateOf(false) }

    if (showImageMapper) {
        ImageMapperScreen(onBack = { showImageMapper = false })
    } else if (showProgressTracker) {
        ProgressTrackerScreen(onBack = { showProgressTracker = false })
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Progress Photos",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Button(
                    onClick = { showImageMapper = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Image Mapper", style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { showProgressTracker = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Progress Tracker", style = MaterialTheme.typography.titleMedium)
                }
            }

            // Banner Ad at bottom
            BannerAds()
        }
    }
}

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageMapperScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val imageViewModel: ImageViewModel = hiltViewModel()
    val imageToProgressViewModel: ImageToProgressViewModel = hiltViewModel()

    val state by imageViewModel.getAllImageState.collectAsState()
    val upsertState by imageToProgressViewModel.upsertImageToProgressState.collectAsState()

    var hasPermission by remember { mutableStateOf(false) }
    var permissionDenied by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableStateOf<com.scrymz.bitebuddy.data.local.model.model.Images?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }

    // Determine which permission to request
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
        permissionDenied = !isGranted
        if (isGranted) {
            imageViewModel.getAllImage()
        }
    }

    // Check permission on launch
    LaunchedEffect(Unit) {
        val permissionStatus = ContextCompat.checkSelfPermission(context, permission)
        hasPermission = permissionStatus == android.content.pm.PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            imageViewModel.getAllImage()
        } else {
            permissionLauncher.launch(permission)
        }
    }

    // Reload data after successful upsert
    LaunchedEffect(upsertState.message) {
        if (upsertState.message.isNotEmpty()) {
            showAddDialog = false
            selectedImage = null
            imageToProgressViewModel.resetUpsertState()
            imageToProgressViewModel.getAllImageToProgressDescending()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Image Mapper") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                when {
                    permissionDenied && !hasPermission -> {
                        PermissionDeniedContent(
                            onRetry = {
                                permissionDenied = false
                                permissionLauncher.launch(permission)
                            }
                        )
                    }

                    !hasPermission -> {
                        LoadingContent("Requesting permission...")
                    }

                    state.isLoading -> {
                        LoadingContent("Loading images...")
                    }

                    state.error != null && state.error!!.isNotEmpty() -> {
                        ErrorContent(state.error!!, onBack)
                    }

                    state.data.isEmpty() -> {
                        EmptyContent("No images found.\nTake photos to track your progress!")
                    }

                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colorScheme.background),
                            contentPadding = PaddingValues(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(state.data) { image ->
                                AsyncImage(
                                    model = image.path.toUri(),
                                    contentDescription = image.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                        .clickable {
                                            selectedImage = image
                                            showAddDialog = true
                                        }
                                )
                            }
                        }
                    }
                }
            }

            BannerAds()
        }
    }

    // Show Add Dialog
    if (showAddDialog && selectedImage != null) {
        val activity = LocalContext.current as? ComponentActivity

        AddImageToProgressDialog(
            image = selectedImage!!,
            onDismiss = {
                showAddDialog = false
                selectedImage = null
            },
            onSave = { imageToProgress ->
                // Save the progress image
                imageToProgressViewModel.upsertImageToProgress(imageToProgress)

                // Show interstitial ad after save
                activity?.let {
                    Log.d("ProgressScreen", "Attempting to show interstitial ad")
                    InterstitialAdHelper.showAd(
                        activity = it,
                        onAdDismissed = {
                            Log.d("ProgressScreen", "Ad dismissed, closing dialog")
                            showAddDialog = false
                            selectedImage = null
                        },
                        onAdFailed = {
                            Log.d("ProgressScreen", "Ad failed or not ready, closing dialog anyway")
                            showAddDialog = false
                            selectedImage = null
                        }
                    )
                } ?: run {
                    // If activity is null, just close dialog
                    Log.w("ProgressScreen", "Activity is null, cannot show ad")
                    showAddDialog = false
                    selectedImage = null
                }
            },
            isLoading = upsertState.isLoading
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProgressTrackerScreen(onBack: () -> Unit) {
    val imageToProgressViewModel: ImageToProgressViewModel = hiltViewModel()
    val allProgressState by imageToProgressViewModel.allImageToProgressState.collectAsState()
    val deleteState by imageToProgressViewModel.deleteImageToProgressState.collectAsState()

    var showDeleteConfirm by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<ImageToProgress?>(null) }

    LaunchedEffect(Unit) {
        imageToProgressViewModel.getAllImageToProgressDescending()
    }

    // Reload after delete
    LaunchedEffect(deleteState.message) {
        if (deleteState.message.isNotEmpty()) {
            showDeleteConfirm = false
            itemToDelete = null
            imageToProgressViewModel.resetDeleteState()
            imageToProgressViewModel.getAllImageToProgressDescending()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Progress Tracker") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                when {
                    allProgressState.isLoading -> {
                        LoadingContent("Loading progress...")
                    }

                    allProgressState.error.isNotEmpty() -> {
                        ErrorContent(allProgressState.error, onBack)
                    }

                    allProgressState.data.isEmpty() -> {
                        EmptyContent("No progress images added yet.\nUse Image Mapper to add images!")
                    }

                    else -> {
                        val pagerState = rememberPagerState(pageCount = { allProgressState.data.size })

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxSize()
                        ) { page ->
                            val progressItem = allProgressState.data[page]
                            ProgressImageCard(
                                progressItem = progressItem,
                                onDelete = {
                                    itemToDelete = progressItem
                                    showDeleteConfirm = true
                                }
                            )
                        }
                    }
                }
            }

            BannerAds()
        }
    }

    // Delete Confirmation Dialog
    if (showDeleteConfirm && itemToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Delete Progress Image") },
            text = { Text("Are you sure you want to delete this progress image?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        imageToProgressViewModel.deleteImageToProgress(itemToDelete!!)
                    },
                    enabled = !deleteState.isLoading
                ) {
                    if (deleteState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    } else {
                        Text("Delete")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ProgressImageCard(
    progressItem: ImageToProgress,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Image
            AsyncImage(
                model = progressItem.imagePath.toUri(),
                contentDescription = progressItem.imageTitle,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            // Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = progressItem.imageTitle,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    IconButton(onClick = onDelete) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Date: ${progressItem.imgDate}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Author: ${progressItem.imageAuthor}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

                if (progressItem.notes.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Notes:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = progressItem.notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddImageToProgressDialog(
    image: com.scrymz.bitebuddy.data.local.model.model.Images,
    onDismiss: () -> Unit,
    onSave: (ImageToProgress) -> Unit,
    isLoading: Boolean
) {
    var title by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(Date()) }
    var showDatePicker by remember { mutableStateOf(false) }

    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Add Progress Image",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Image Preview
                AsyncImage(
                    model = image.path.toUri(),
                    contentDescription = image.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Author
                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Author") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Date Picker
                OutlinedTextField(
                    value = dateFormatter.format(selectedDate),
                    onValueChange = { },
                    label = { Text("Date") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                    enabled = false,
                    readOnly = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Notes
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss, enabled = !isLoading) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (title.isNotEmpty() && author.isNotEmpty()) {
                                val imageToProgress = ImageToProgress(
                                    imagePath = image.path,
                                    imageTitle = title,
                                    notes = notes,
                                    imageAuthor = author,
                                    imgDate = dateFormatter.format(selectedDate)
                                )
                                onSave(imageToProgress)
                            }
                        },
                        enabled = !isLoading && title.isNotEmpty() && author.isNotEmpty()
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text("Save")
                        }
                    }
                }
            }
        }
    }

    // Simple Date Picker (you can use Material3 DatePicker for better UX)
    if (showDatePicker) {
        AlertDialog(
            onDismissRequest = { showDatePicker = false },
            title = { Text("Select Date") },
            text = {
                Text("Use your device's date picker or enter manually.\nCurrent: ${dateFormatter.format(selectedDate)}")
            },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("OK")
                }
            }
        )
    }
}

// Helper Composables
@Composable
fun PermissionDeniedContent(onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Locked",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(64.dp)
            )

            Text(
                text = "Feature Restricted",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )

            Text(
                text = "This feature requires permission to access your photos.\n\nPlease grant permission to continue.",
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )

            Button(onClick = onRetry) {
                Text("Grant Permission")
            }
        }
    }
}

@Composable
fun LoadingContent(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ErrorContent(error: String, onBack: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Error: $error",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
            Button(onClick = onBack) {
                Text("Go Back")
            }
        }
    }
}

@Composable
fun EmptyContent(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}
