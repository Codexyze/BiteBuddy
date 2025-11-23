package com.scrymz.bitebuddy.presentation.screens

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.scrymz.bitebuddy.data.entity.ImageToProgress
import com.scrymz.bitebuddy.presentation.utils.InterstitialAdHelper
import com.scrymz.bitebuddy.presentation.viewmodels.ImageToProgressViewModel
import com.scrymz.bitebuddy.presentation.viewmodels.ImageViewModel
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageTrackerScreen(navController: NavController) {
    val context = LocalContext.current
    val imageViewModel: ImageViewModel = hiltViewModel()
    val imageToProgressViewModel: ImageToProgressViewModel = hiltViewModel()

    val foldersState by imageViewModel.getImageFoldersState.collectAsState()
    val imagesState by imageViewModel.getImagesFromFolderState.collectAsState()
    val allImagesState by imageViewModel.getAllImageState.collectAsState()
    val upsertState by imageToProgressViewModel.upsertImageToProgressState.collectAsState()

    var hasPermission by remember { mutableStateOf(false) }
    var permissionDenied by remember { mutableStateOf(false) }
    var selectedFolder by remember { mutableStateOf<String?>(null) }
    var selectedImage by remember { mutableStateOf<com.scrymz.bitebuddy.data.local.model.model.Images?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showAllImages by remember { mutableStateOf(false) }

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
            imageViewModel.getImageFolders()
        }
    }

    // Check permission on launch
    LaunchedEffect(Unit) {
        val permissionStatus = ContextCompat.checkSelfPermission(context, permission)
        hasPermission = permissionStatus == android.content.pm.PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            imageViewModel.getImageFolders()
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
                title = {
                    Text(
                        when {
                            showAllImages -> "All Images"
                            selectedFolder != null -> selectedFolder!!
                            else -> "Image Tracker"
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        when {
                            showAllImages -> showAllImages = false
                            selectedFolder != null -> selectedFolder = null
                            else -> navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                        ImageTrackerPermissionDeniedContent(
                            onRetry = {
                                permissionDenied = false
                                permissionLauncher.launch(permission)
                            }
                        )
                    }

                    !hasPermission -> {
                        ImageTrackerLoadingContent("Requesting permission...")
                    }

                    showAllImages -> {
                        // Show all images
                        when {
                            allImagesState.isLoading -> {
                                ImageTrackerLoadingContent("Loading all images...")
                            }

                            allImagesState.error != null && allImagesState.error!!.isNotEmpty() -> {
                                ImageTrackerErrorContent(allImagesState.error!!) { showAllImages = false }
                            }

                            allImagesState.data.isEmpty() -> {
                                ImageTrackerEmptyContent("No images found.\nTake photos to track your progress!")
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
                                    items(allImagesState.data) { image ->
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

                    selectedFolder == null -> {
                        // Show folders + "All Images" option
                        when {
                            foldersState.isLoading -> {
                                ImageTrackerLoadingContent("Loading folders...")
                            }

                            foldersState.error.isNotEmpty() -> {
                                ImageTrackerErrorContent(foldersState.error) { navController.popBackStack() }
                            }

                            foldersState.data.isEmpty() -> {
                                ImageTrackerEmptyContent("No image folders found.\nTake photos to track your progress!")
                            }

                            else -> {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = MaterialTheme.colorScheme.background),
                                    contentPadding = PaddingValues(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    // "All Images" card first
                                    item {
                                        AllImagesCard(
                                            totalImages = foldersState.data.sumOf { it.imageCount },
                                            onClick = {
                                                showAllImages = true
                                                imageViewModel.getAllImage()
                                            }
                                        )
                                    }

                                    // Folder cards
                                    items(foldersState.data) { folder ->
                                        ImageTrackerFolderCard(
                                            folder = folder,
                                            onClick = {
                                                selectedFolder = folder.name
                                                imageViewModel.getImagesFromFolder(folder.name)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    else -> {
                        // Show images from selected folder
                        when {
                            imagesState.isLoading -> {
                                ImageTrackerLoadingContent("Loading images...")
                            }

                            imagesState.error.isNotEmpty() -> {
                                ImageTrackerErrorContent(imagesState.error) { selectedFolder = null }
                            }

                            imagesState.data.isEmpty() -> {
                                ImageTrackerEmptyContent("No images in this folder.")
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
                                    items(imagesState.data) { image ->
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
                }
            }

            BannerAds()
        }
    }

    // Show Add Dialog
    if (showAddDialog && selectedImage != null) {
        val activity = LocalContext.current as? ComponentActivity

        ImageTrackerAddDialog(
            image = selectedImage!!,
            onDismiss = {
                showAddDialog = false
                selectedImage = null
            },
            onSave = { imageToProgress ->
                imageToProgressViewModel.upsertImageToProgress(imageToProgress)

                activity?.let {
                    Log.d("ImageTracker", "Attempting to show interstitial ad")
                    InterstitialAdHelper.showAd(
                        activity = it,
                        onAdDismissed = {
                            Log.d("ImageTracker", "Ad dismissed, closing dialog")
                            showAddDialog = false
                            selectedImage = null
                        },
                        onAdFailed = {
                            Log.d("ImageTracker", "Ad failed or not ready, closing dialog anyway")
                            showAddDialog = false
                            selectedImage = null
                        }
                    )
                } ?: run {
                    Log.w("ImageTracker", "Activity is null, cannot show ad")
                    showAddDialog = false
                    selectedImage = null
                }
            },
            isLoading = upsertState.isLoading
        )
    }
}

// "All Images" Card
@Composable
fun AllImagesCard(
    totalImages: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "All Images",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "All Images",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$totalImages images",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ImageTrackerFolderCard(
    folder: com.scrymz.bitebuddy.data.local.model.model.ImageFolder,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            AsyncImage(
                model = folder.coverImageUri?.toUri(),
                contentDescription = folder.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(12.dp)
            ) {
                Text(
                    text = folder.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${folder.imageCount} images",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageTrackerAddDialog(
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

                AsyncImage(
                    model = image.path.toUri(),
                    contentDescription = image.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Author") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

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

// Helper Composables for ImageTracker
@Composable
fun ImageTrackerPermissionDeniedContent(onRetry: () -> Unit) {
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
fun ImageTrackerLoadingContent(message: String) {
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
fun ImageTrackerErrorContent(error: String, onBack: () -> Unit) {
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
fun ImageTrackerEmptyContent(message: String) {
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
