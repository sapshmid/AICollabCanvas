package com.example.aicollabcanvas

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(val user: Profile, val title: String, val subtitle: String, val text: String, val postPic: Uri? = null) :
    Parcelable