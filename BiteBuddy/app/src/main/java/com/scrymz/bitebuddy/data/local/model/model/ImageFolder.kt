package com.scrymz.bitebuddy.data.local.model.model

data class ImageFolder(
    val name: String,
    val path: String,
    val imageCount: Int,
    val coverImageUri: String? = null
)

