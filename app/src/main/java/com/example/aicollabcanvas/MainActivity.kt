package com.example.aicollabcanvas

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
//        val editProfileButton: Button = findViewById(R.id.btnEditProfile)
//        editProfileButton.setOnClickListener(::onEditStudentButtonClicked)

        displayProfileFragment()
    }

//    fun onEditStudentButtonClicked(view: View) {
//        if (profileFragment == null) {
//            displayProfileFragment()
//        } else
//            removeProfileFragment()
//    }

    fun displayProfileFragment() {
        profileFragment = ProfileFragment()

        profileFragment?.let {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fcMainProfileContainer, it)
            transaction.addToBackStack("Tag")
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