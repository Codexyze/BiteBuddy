package com.scrymz.bitebuddy.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "ImageToProgressTable")
data class ImageToProgress(
    @PrimaryKey(autoGenerate = true)
    val id:Int =0,
    val imagePath: String,
    val imageTitle: String,
    val notes: String,
    val imageAuthor: String,
    val imgDate: String
)
