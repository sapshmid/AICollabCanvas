package com.example.aicollabcanvas

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts


class ProfileFragment : Fragment() {

    var name: String? = null
    var role: String? = null
    var profileName: TextView? = null
    var profileRole: TextView? = null

    var editName: EditText? = null
    var editRole: EditText? = null

    var editProfileButton: ImageButton? = null
    var editMode: Boolean = false

    var pic: Uri? = null
    var profilePic: ImageView? = null
    var editPictureButton: ImageButton? = null

    lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    companion object {

        // Later on, these will be the values received from the registration
        const val NAME = "NAME"
        const val ROLE = "ROLE"

        fun newInstance(name: String, role: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME, name)
                    putString(ROLE, role)
                }
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            name = it.getString(NAME)
            role = it.getString(ROLE)
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    profilePic?.setImageURI(it)
                    pic = it
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profileName = view.findViewById(R.id.tvProfileName)
        profileName?.text = name

        profileRole = view.findViewById(R.id.tvProfileRole)
        profileRole?.text = role

        editName = view.findViewById(R.id.etEditName)
        editName?.setText(name)

        editRole = view.findViewById(R.id.etEditRole)
        editRole?.setText(role)

        editProfileButton = view.findViewById(R.id.ibtnEditProfileButton)
        editProfileButton?.setOnClickListener(::onEditProfileButtonClicked)

        profilePic = view.findViewById(R.id.ivProfilePic)

        editPictureButton = view.findViewById(R.id.ibtnEditPictureButton)
        editPictureButton?.setOnClickListener(::onEditPictureButtonClicked)


        return view
    }

    fun onEditProfileButtonClicked(view: View) {
        if(!editMode) {
            profileName?.visibility = View.GONE
            profileRole?.visibility = View.GONE

            editName?.visibility = View.VISIBLE
            editRole?.visibility = View.VISIBLE

            editProfileButton?.setImageResource(R.drawable.save_edit)
            editPictureButton?.visibility = View.VISIBLE

            editMode = true
        } else {
            name = editName?.text.toString()
            profileName?.text = name

            role = editRole?.text.toString()
            profileRole?.text = role

            profileName?.visibility = View.VISIBLE
            profileRole?.visibility = View.VISIBLE

            editName?.visibility = View.GONE
            editRole?.visibility = View.GONE

            editProfileButton?.setImageResource(R.drawable.edit_pencil_small)
            editPictureButton?.visibility = View.GONE

            editMode = false
        }


    }

    fun onEditPictureButtonClicked(view: View) {

        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        galleryLauncher.launch(galleryIntent)


    }



}
