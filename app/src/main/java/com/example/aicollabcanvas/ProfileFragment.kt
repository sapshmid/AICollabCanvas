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

    var pic: Uri? = null
    var profilePic: ImageView? = null

    var editedPic: Uri? = null
    var editPictureButton: ImageButton? = null

    lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    var saveProfileEditButton: ImageButton? = null
    var cancelProfileEditButton: ImageButton? = null

    companion object {

        // Later on, these will be the values received from the registration
        const val NAME = "NAME"
        const val ROLE = "ROLE"
        const val PIC = "PIC"

        fun newInstance(profile: Profile) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME, profile.name)
                    putString(ROLE, profile.role)
                    putString(PIC, profile.pic.toString())
                }
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            name = it.getString(NAME)
            role = it.getString(ROLE)
            pic = Uri.parse(it.getString(PIC))
            editedPic = pic
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    profilePic?.setImageURI(it)
                    editedPic = it
                }
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(NAME, name)
        outState.putString(ROLE, role)
        outState.putString(PIC, pic.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Restore saved instance state
        savedInstanceState?.let {
            name = it.getString(NAME)
            role = it.getString(ROLE)
            pic = Uri.parse(it.getString(PIC))
        }

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
        profilePic?.setImageURI(pic)
        profilePic?.visibility = View.VISIBLE

        editPictureButton = view.findViewById(R.id.ibtnEditPictureButton)
        editPictureButton?.setOnClickListener(::onEditPictureButtonClicked)

        saveProfileEditButton = view.findViewById(R.id.ibtnSaveButton)
        saveProfileEditButton?.setOnClickListener(::onSaveEditButtonClicked)

        cancelProfileEditButton = view.findViewById(R.id.ibtnCancelButton)
        cancelProfileEditButton?.setOnClickListener(::onCancelEditButtonClicked)

        return view
    }

    fun onEditProfileButtonClicked(view: View) {

        profileName?.visibility = View.GONE
        profileRole?.visibility = View.GONE

        editName?.visibility = View.VISIBLE
        editRole?.visibility = View.VISIBLE
        editPictureButton?.visibility = View.VISIBLE

        saveProfileEditButton?.visibility = View.VISIBLE
        cancelProfileEditButton?.visibility = View.VISIBLE

        editProfileButton?.visibility = View.GONE
    }

    fun onEditPictureButtonClicked(view: View) {

        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        galleryLauncher.launch(galleryIntent)

    }

    fun onSaveEditButtonClicked(view: View) {

        name = editName?.text.toString()
        profileName?.text = name
        pic = editedPic

        role = editRole?.text.toString()
        profileRole?.text = role

        profileName?.visibility = View.VISIBLE
        profileRole?.visibility = View.VISIBLE

        editName?.visibility = View.GONE
        editRole?.visibility = View.GONE
        editPictureButton?.visibility = View.GONE

        saveProfileEditButton?.visibility = View.GONE
        cancelProfileEditButton?.visibility = View.GONE

        editProfileButton?.visibility = View.VISIBLE
    }

    fun onCancelEditButtonClicked(view: View) {

        editName?.setText(name)
        editRole?.setText(role)

        editedPic = pic
        profilePic?.setImageURI(pic)

        profileName?.visibility = View.VISIBLE
        profileRole?.visibility = View.VISIBLE

        editName?.visibility = View.GONE
        editRole?.visibility = View.GONE
        editPictureButton?.visibility = View.GONE

        saveProfileEditButton?.visibility = View.GONE
        cancelProfileEditButton?.visibility = View.GONE
        editProfileButton?.visibility = View.VISIBLE
    }

}
