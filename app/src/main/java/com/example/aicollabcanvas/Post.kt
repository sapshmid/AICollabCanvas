package com.example.aicollabcanvas

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity @Parcelize
data class Post(
    @PrimaryKey val user: Profile,
    val title: String,
    val subtitle: String,
    val text: String,
    val postPic: Uri? = null) :
    Parcelable