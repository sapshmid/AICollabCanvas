package com.example.aicollabcanvas

import android.widget.ImageView
import com.squareup.picasso.Picasso

object Utils {
    fun setImageIntoView(view: ImageView, imageUri: String?, errorId: Int) {
        if (imageUri != null && imageUri.trim() != "") {
            Picasso.get()
                .load(imageUri)
                .error(errorId)  // Image shown if there's an error
                .into(view)
        } else
            view.setImageResource(errorId)
    }
}