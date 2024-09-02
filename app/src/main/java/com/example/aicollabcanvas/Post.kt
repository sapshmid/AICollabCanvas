package com.example.aicollabcanvas

import android.net.Uri

data class Post(val user: Profile, val title: String, val subtitle: String, val text: String, val postPic: Uri? = null)