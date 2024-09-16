package com.example.aicollabcanvas

import android.app.Activity
import android.content.Intent
import android.media.SubtitleData
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation


class AddPostFragment : Fragment() {

    var profile: Profile? = null

    var profilePic: ImageView? = null
    var profileName: TextView? = null
    var profileRole: TextView? = null

    var title: String? = null
    var addTitle: EditText? = null

    var subtitle: String? = null
    var addSubtitle: EditText? = null

    var postPic: Uri? = null
    var postPicView: ImageView? = null

    var text: String? = null
    var addText: EditText? = null

    var addPictureButton: Button? = null
    lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    companion object {
        const val PROFILE = "profile"
        const val POST = "post"

        fun newInstance(profile: Profile, post: Post? = null) =
            AddPostFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PROFILE, profile)
                    post?.let {
                        putParcelable(POST, post);
                    }
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            profile = it.getParcelable(PROFILE)
            val post: Post? = it.getParcelable(POST)
            post?.let {
                text = post.text
                subtitle = post.subtitle
                title = post.title
                postPic = post.postPic
            }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    postPicView?.setImageURI(it)
                    postPic = it
                    postPicView?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add_post, container, false)

        val btnCancelPost: Button = view.findViewById(R.id.btnCancelPost)
        val btnSubmit: Button = view.findViewById(R.id.btnSubmit)

        profilePic = view.findViewById(R.id.ivProfilePic)
        profilePic?.setImageURI(profile?.pic)

        profileName = view.findViewById(R.id.tvProfileName)
        profileName?.text = profile?.name

        profileRole = view.findViewById(R.id.tvProfileRole)
        profileRole?.text = profile?.role

        addTitle = view.findViewById(R.id.etAddTitle)
        addTitle?.setText(title)

        addSubtitle = view.findViewById(R.id.etAddSubtitle)
        addSubtitle?.setText(subtitle)

        postPicView = view.findViewById(R.id.ivPostPic)
        postPicView?.visibility = View.GONE
        postPic?.let {
            postPicView?.setImageURI(it)
            postPicView?.visibility = View.VISIBLE
        }

        addText = view.findViewById(R.id.etAddText)
        addText?.setText(text)

        addPictureButton = view.findViewById(R.id.btnAddPicture)
        addPictureButton?.setOnClickListener(::onAddPictureButtonClicked)
        if (profile?.role == "Community")
            addPictureButton?.visibility = View.GONE

       btnCancelPost.setOnClickListener{
          Navigation.findNavController(view).popBackStack()
       }

       btnSubmit.setOnClickListener{
           Navigation.findNavController(view).navigate(R.id.action_addPostFragment_to_feedFragment)
        }

        return view
    }

    fun onAddPictureButtonClicked(view: View) {

        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        galleryLauncher.launch(galleryIntent)
    }


}