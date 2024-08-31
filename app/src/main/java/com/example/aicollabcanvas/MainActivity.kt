package com.example.aicollabcanvas

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    var profileFragment: ProfileFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayProfileFragment()
    }



    fun displayProfileFragment() {
        profileFragment = ProfileFragment.newInstance("Name","Role", Uri.parse("android.resource://com.example.aicollabcanvas/${R.drawable.empty_profile}"))
        profileFragment?.let { fragment ->
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fcMainProfileContainer, fragment)
            transaction.commit()
        }

    }

    fun removeProfileFragment() {
        profileFragment?.let {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(it)
            transaction.addToBackStack("Tag")
            transaction.commit()
        }
        profileFragment = null

    }
}