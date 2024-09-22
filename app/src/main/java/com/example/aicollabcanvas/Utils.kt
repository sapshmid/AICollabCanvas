package com.example.aicollabcanvas

import android.widget.ImageView
import com.squareup.picasso.Picasso

object Utils {
    fun setImageIntoView(view: ImageView, imageUri: String?, placeholder: Int?) {
        if (imageUri != null && imageUri.trim() != "") {
            val picasso = Picasso.get().load(imageUri)
            placeholder?.let {
                picasso.placeholder(it)
            }
            picasso.error(R.drawable.failed_to_load)
                .into(view)
        } else
            view.setImageResource(placeholder ?: R.drawable.failed_to_load)
    }
}