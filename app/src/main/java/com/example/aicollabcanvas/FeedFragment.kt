package com.example.aicollabcanvas

import PostAdapter
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FeedFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var cpiFeedProgress: CircularProgressIndicator
    private var posts = mutableListOf<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        cpiFeedProgress = view.findViewById(R.id.cpiFeedProgress)
        recyclerView = view.findViewById(R.id.rvFeedPostsContainer)
        recyclerView.layoutManager = LinearLayoutManager(context)

        initializePostList()
        return view
    }

    private fun initializePostList() {
        posts.clear()
        cpiFeedProgress.visibility = View.VISIBLE
        FirebaseFirestore.getInstance().collection("posts").get().addOnSuccessListener { result ->
            for (document in result) {
                val post = document.toObject(Post::class.java)
                val profileRef = document.getDocumentReference("profileId")
                profileRef?.get()?.addOnSuccessListener { profileDocument ->
                    if (profileDocument.exists()) {
                        val profile = profileDocument.toObject(UserProfile::class.java)
                        post.profile = profile
                        posts.add(post)
                        updateRecyclerView()
                    }
                }
            }
            cpiFeedProgress.visibility = View.GONE
        }.addOnFailureListener {
            Toast.makeText(context, "Error fetching posts: ${it.message}", Toast.LENGTH_SHORT).show()
            cpiFeedProgress.visibility = View.GONE
        }
    }

    private fun updateRecyclerView() {
        val adapter = PostAdapter(posts, null, showEditButtons =  false)
        recyclerView.adapter = adapter
    }

}