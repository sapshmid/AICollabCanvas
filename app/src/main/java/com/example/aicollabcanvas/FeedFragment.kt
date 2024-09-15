package com.example.aicollabcanvas

import PostAdapter
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FeedFragment : Fragment() {

    companion object {

        fun newInstance() =
            FeedFragment().apply {
                arguments = Bundle().apply {

                }
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rvFeedPostsContainer)
        recyclerView.layoutManager = LinearLayoutManager(context)

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

        val adapter: PostAdapter = PostAdapter(postList, object : PostAdapter.OnPostInteractionListener {
            override fun onDeletePost(position: Int) {
            }

            override fun onEditPost(position: Int) {
            }

        }, showEditButtons = false)


        recyclerView.adapter = adapter
        return view
    }


}