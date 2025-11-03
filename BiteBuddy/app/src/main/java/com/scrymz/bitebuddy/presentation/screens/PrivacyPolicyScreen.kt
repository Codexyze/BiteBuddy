package com.scrymz.bitebuddy.presentation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(navController: NavController) {
    val context = LocalContext.current // ✅ Required to open browser

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Privacy Policy",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                // 📜 Main Privacy Content
                Text(
                    text = "BiteBuddy Privacy Policy\n\nEffective Date: November 3, 2025\n\n" +
                            "At BiteBuddy, your privacy matters to us. This Privacy Policy explains how we handle your data while you use our app.\n\n" +
                            "By using BiteBuddy, you consent to this Privacy Policy and agree to its terms.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "1️⃣ Data Collection & Storage",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "BiteBuddy does not collect, store, or transmit any personal or sensitive user data to external servers. " +
                            "All your app usage data stays locally on your device, including:\n\n" +
                            "• Food consumption logs and nutrition tracking\n" +
                            "• Exercise and workout records\n" +
                            "• Water intake logs\n" +
                            "• Menstrual cycle tracking data\n" +
                            "• Progress photos (stored locally)\n" +
                            "• Personal notes and health metrics\n\n" +
                            "Your data never leaves your device and is completely under your control.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "2️⃣ Device Storage Access",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "BiteBuddy requests access to your device storage (READ_MEDIA_IMAGES permission) only for the Progress Tracking feature. " +
                            "This permission allows you to:\n\n" +
                            "• Select and save progress photos from your device gallery\n" +
                            "• Track your fitness/health journey visually\n\n" +
                            "These images are stored locally on your device and are never uploaded or shared. " +
                            "You can revoke this permission anytime from Settings → Apps → BiteBuddy → Permissions → Photos and videos.\n\n" +
                            "If denied, the progress photo feature will be disabled, but all other features will continue to function normally.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "3️⃣ Internet Usage",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "BiteBuddy uses the internet only to display banner ads and optional feedback ads. " +
                            "No personal data (food logs, exercise records, menstrual data, or any other information) is ever transmitted to our servers. " +
                            "Internet usage occurs exclusively for ad-serving through trusted networks like Google AdMob.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "4️⃣ Advertisements",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "BiteBuddy displays banner ads and optional feedback ads via Google AdMob. " +
                            "These ads may use anonymous identifiers for personalization.\n\n" +
                            "• Banner ads appear at the bottom of screens\n" +
                            "• Optional feedback ads shown only when you tap 'Support via Ad'\n" +
                            "• No forced full-screen ads, pop-ups, or auto-play ads\n\n" +
                            "You can opt out of personalized ads: Settings → Google → Ads → Opt out of Ads Personalization.\n\n" +
                            "Note: Ads may take a few days to display after release, depending on AdMob's review.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "5️⃣ Food & Nutrition Data Accuracy",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "The nutrition dataset in BiteBuddy is manually created and intended for general wellness awareness. " +
                            "It is not medically certified and should not replace professional advice. " +
                            "Consult a healthcare professional or registered dietitian for specific dietary guidance.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "6️⃣ Third-Party Services",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "We use trusted third-party ad providers (like Google AdMob) to serve advertisements. " +
                            "No personal data, exercise logs, or health records are shared with these providers. " +
                            "BiteBuddy does not use analytics SDKs, external tracking tools, or cloud databases.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "7️⃣ Data Security",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "All user data is stored locally using Room Database. We recommend:\n\n" +
                            "• Keep your device locked with a strong password or PIN\n" +
                            "• Regularly back up device data\n" +
                            "• Keep your Android OS up-to-date\n\n" +
                            "Important: BiteBuddy cannot access or recover your data if you uninstall the app or lose your device.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "8️⃣ Children's Privacy",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "BiteBuddy is designed for users aged 13 and above. " +
                            "We do not knowingly collect information from children under 13. " +
                            "If you believe your child has provided information, please contact us for assistance.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "9️⃣ Changes to This Policy",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "We may occasionally update this Privacy Policy to reflect feature changes, compliance requirements, or legal obligations. " +
                            "Updates will be posted here and on our official website. Please review this policy periodically for the latest version.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "🔟 Contact Us",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "If you have any questions or concerns about this Privacy Policy:\n\n" +
                            "Email: nutrinonovarage@gmail.com\n" +
                            "Developer Portfolio: https://v0-akshay-sarapure-portfolio.vercel.app/\n" +
                            "Website: https://codexyze.github.io/bitebuddy.html",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 📝 User-Friendly Summary Note
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "📋 Summary (Simple Language)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "✅ BiteBuddy is an offline, privacy-first fitness app\n" +
                                    "✅ All your data stays on your device — we don't collect or share anything\n" +
                                    "✅ Storage permission is only for local progress photos\n" +
                                    "✅ Internet is used only for banner/optional ads\n" +
                                    "✅ Food data is general-purpose, not for medical use\n" +
                                    "✅ No analytics, no tracking, no external servers\n" +
                                    "✅ Your health and privacy are completely under your control",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 🌐 Open Full Policy Online Button
                TextButton(
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://codexyze.github.io/bitebuddy.html")
                        )
                        context.startActivity(intent)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        imageVector = Icons.Default.OpenInNew,
                        contentDescription = "Open Website",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("View Full Policy Online", color = MaterialTheme.colorScheme.primary)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // ✅ Back Button
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Go Back")
                }
            }

            // Banner Ad at bottom
            BannerAds()
        }
    }
}
