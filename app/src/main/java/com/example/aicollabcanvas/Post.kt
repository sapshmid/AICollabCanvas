package com.example.aicollabcanvas

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Post(
    var id: String,
    var profile: UserProfile?,
    val title: String,
    val subtitle: String,
    val text: String,
    val postPic: String? = null) {
    constructor() : this("", null, "", "", "", null)
}