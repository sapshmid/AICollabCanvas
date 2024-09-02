package com.example.aicollabcanvas

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.aicollabcanvas.ProfileFragment.Companion
import java.net.URI

class PostFragment : Fragment() {

    // name, role and pic will later on be taken from the database
    // also the post information will be saved in the database from all the posts created in add post
    var name: String? = null
    var postProfileName: TextView? = null

    var role: String? = null
    var postProfileRole: TextView? = null

    var pic: Uri? = null
    var postProfilePic: ImageView? = null

    var replayPic: Uri? = null
    var postReplayPic: ImageView? = null

    var title: String? = null
    var postTitle: TextView? = null

    var subtitle: String? = null
    var postSubtitle: TextView? = null

    var text: String? = null
    var postText: TextView? = null

    //add delete post button

    // For editing the post, the edit button will transfer the user to the "add post" fragment
    // with the current data already filled in the boxes to allow changes


    companion object {

        const val NAME = "NAME"
        const val ROLE = "ROLE"
        const val PIC = "PIC"
        const val POST_PIC = "POST_PIC"
        const val TITLE = "TITLE"
        const val SUBTITLE = "SUBTITLE"
        const val TEXT = "TEXT"

        fun newInstance(post: Post?) =
            PostFragment().apply {
                arguments = Bundle().apply {
                    post?.apply {
                        putString(NAME, post.user.name)
                        putString(ROLE, post.user.role)
                        putString(PIC, post.user.pic.toString())
                        putString(POST_PIC, post.postPic.toString())
                        putString(TITLE, post.title)
                        putString(SUBTITLE, post.subtitle)
                        putString(TEXT, post.text)
                    }
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(NAME)
            role = it.getString(ROLE)
            pic = Uri.parse(it.getString(PIC))
            replayPic = Uri.parse(it.getString(POST_PIC))
            title = it.getString(TITLE)
            subtitle = it.getString(SUBTITLE)
            text = it.getString(TEXT)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(NAME, name)
        outState.putString(ROLE, role)
        outState.putString(PIC, pic.toString())
        outState.putString(POST_PIC, replayPic.toString())
        outState.putString(TITLE, title)
        outState.putString(SUBTITLE, subtitle)
        outState.putString(TEXT, text)
    }

    fun updatePost(post: Post) {
        name = post.user.name
        role = post.user.role
        pic = post.user.pic
        replayPic = post.postPic
        title = post.title
        subtitle = post.subtitle
        text = post.text
        updatePostViews()
    }

    fun updatePostViews() {
        postProfileName?.text = name
        postProfileRole?.text = role
        postProfilePic?.setImageURI(pic)
        if (role == "Contributor" && replayPic != null) {
            postReplayPic?.visibility = View.VISIBLE
            postReplayPic?.setImageURI((replayPic))
        }
        else {
            postReplayPic?.visibility = View.GONE
        }

        postTitle?.text = title
        postSubtitle?.text = subtitle
        postText?.text = text
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)

        savedInstanceState?.let {
            name = it.getString(NAME)
            role = it.getString(ROLE)
            pic = Uri.parse(it.getString(PIC))
            replayPic = Uri.parse(it.getString(POST_PIC))
            title = it.getString(TITLE)
            subtitle = it.getString(SUBTITLE)
            text = it.getString(TEXT)
        }

        postProfileName = view.findViewById(R.id.tvProfileName)
        postProfileRole = view.findViewById(R.id.tvProfileRole)

        postProfilePic = view.findViewById(R.id.ivProfilePic)
        postReplayPic = view.findViewById(R.id.ivPostReplayPic)

        postTitle = view.findViewById(R.id.tvPostTitle)
        postSubtitle = view.findViewById(R.id.tvPostSubtitle)
        postText = view.findViewById(R.id.tvPostText)

        updatePostViews()

        return view
    }


}