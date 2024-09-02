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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    var profileFragment: ProfileFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayProfileFragment()
        displayPostFragment()
    }



    fun displayProfileFragment() {
        profileFragment = ProfileFragment.newInstance(Profile("Name","Role", Uri.parse("android.resource://com.example.aicollabcanvas/${R.drawable.empty_profile}")))
        profileFragment?.let { fragment ->
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fcMainProfileContainer, fragment)
            transaction.commit()
        }

    }

    fun displayPostFragment() {
        val recyclerView: RecyclerView = findViewById(R.id.rvMainPostContainer)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val postList = mutableListOf(
            Post(Profile("Someone", "Community", Uri.parse("android.resource://com.example.aicollabcanvas/${R.drawable.person2}")),
                "Picture needed", "some picture", "I need a picture in AI gxdgrsxdbdkjdvkl  dfisd jsdifj sd idsjfio siodf j ioszjedf diog xdf gxdxdj goixd"),
            Post(Profile("NewGuy", "Contributor", Uri.parse("android.resource://com.example.aicollabcanvas/${R.drawable.person8}")),
                "Here is your picture", "some AI picture", "This is the picture that you needed in AI gxdgrsxdbdkjdvkl  dfisd jsdifj sd idsjfio siodf j ioszjedf diog xdf gxdxdj goixd", Uri.parse("android.resource://com.example.aicollabcanvas/${R.drawable.post_pic}")),
            Post(Profile("Someone", "Community", Uri.parse("android.resource://com.example.aicollabcanvas/${R.drawable.person2}")),
                "Picture needed", "some picture", "I need a picture in AI gxdgrsxdbdkjdvkl  dfisd jsdifj sd idsjfio siodf j ioszjedf diog xdf gxdxdj goixd"),
            Post(Profile("NewGuy", "Contributor", Uri.parse("android.resource://com.example.aicollabcanvas/${R.drawable.person8}")),
                "Here is your picture", "some AI picture", "This is the picture that you needed in AI gxdgrsxdbdkjdvkl  dfisd jsdifj sd idsjfio siodf j ioszjedf diog xdf gxdxdj goixd", Uri.parse("android.resource://com.example.aicollabcanvas/${R.drawable.post_pic}")),
        )

        lateinit var adapter: PostAdapter
        adapter = PostAdapter(postList, object : PostAdapter.OnPostInteractionListener {
            override fun onDeletePost(position: Int) {
                // Handle the delete action, e.g., remove the item from your dataset and notify the adapter
                postList.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        })

        recyclerView.adapter = adapter
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