package com.example.aicollabcanvas

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity @Parcelize
data class Profile(
    @PrimaryKey val name: String,
    val role: String,
    val pic: Uri) : Parcelable