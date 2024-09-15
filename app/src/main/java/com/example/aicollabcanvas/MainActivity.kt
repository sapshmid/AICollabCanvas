package com.example.aicollabcanvas

import PostAdapter
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aicollabcanvas.ui.login.RegisterFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    var profileFragment: ProfileFragment? = null
    //var addPostFragment: AddPostFragment? = null
    var feedFragment: FeedFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //displayProfileFragment()
        //displayAddPostFragment()
        displayPostFragment()

    }


    fun displayProfileFragment() {
        profileFragment = ProfileFragment.newInstance(Profile("Name","Community", Uri.parse("android.resource://com.example.aicollabcanvas/${R.drawable.empty_profile}")))
        profileFragment?.let { fragment ->
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fcMainProfileContainer, fragment)
            transaction.commit()
        }

    }

    fun displayAddPostFragment(post: Post) {

        val addPostFragment = AddPostFragment.newInstance(post.user,post)
        addPostFragment.let { fragment ->
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fcMainAddPostContainer, addPostFragment, "tag")
            transaction.addToBackStack("Tag")
            transaction.commit()

        }
    }

    fun displayPostFragment() {
        feedFragment = FeedFragment.newInstance()
        feedFragment?.let { fragment ->
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fcMainProfileContainer, fragment)
            transaction.commit()
        }
    }

}

