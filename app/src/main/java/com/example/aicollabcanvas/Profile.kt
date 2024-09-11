package com.example.aicollabcanvas

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(val name: String, val role: String, val pic: Uri) : Parcelable