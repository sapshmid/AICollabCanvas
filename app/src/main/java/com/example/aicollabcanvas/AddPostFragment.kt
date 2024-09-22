package com.example.aicollabcanvas

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


class AddPostFragment : Fragment() {

    private var postId: String? = null
    private var postPicUrl: String? = null
    private var originalPostPicUrl: String? = null

    private lateinit var clPostFormLayout: ConstraintLayout

    private lateinit var profilePic: ImageView
    private lateinit var profileName: TextView
    private lateinit var profileRole: TextView

    private lateinit var addTitle: EditText
    private lateinit var addSubtitle: EditText
    private lateinit var postPicView: ImageView
    private lateinit var addText: EditText
    private lateinit var cpiAddPostProgress: CircularProgressIndicator
    private lateinit var cpiEditPostProgress: CircularProgressIndicator

    private lateinit var btnSubmit: Button
    private lateinit var btnCancel: Button
    private lateinit var addPictureButton: Button
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    postPicView.setImageURI(it)
                    postPicUrl = it.toString()
                    postPicView.visibility = View.VISIBLE
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

        val profile = GlobalProfileManager.getProfile()
        btnCancel = view.findViewById(R.id.btnCancelPost)
        btnSubmit = view.findViewById(R.id.btnSubmit)

        profilePic = view.findViewById(R.id.ivProfilePic)
        Utils.setImageIntoView(profilePic, profile?.profilePic, R.drawable.empty_profile)

        profileName = view.findViewById(R.id.tvProfileName)
        profileName.text = profile?.name

        profileRole = view.findViewById(R.id.tvProfileRole)
        profileRole.text = profile?.role

        addTitle = view.findViewById(R.id.etAddTitle)

        addSubtitle = view.findViewById(R.id.etAddSubtitle)

        postPicView = view.findViewById(R.id.ivPostPic)
        postPicView.visibility = View.GONE

        addText = view.findViewById(R.id.etAddText)


        addPictureButton = view.findViewById(R.id.btnAddPicture)
        addPictureButton.setOnClickListener(::onAddPictureButtonClicked)
        if (profile?.role != "Contributor")
            addPictureButton.visibility = View.GONE

        cpiAddPostProgress = view.findViewById(R.id.cpiAddPostProgress)
        cpiEditPostProgress = view.findViewById(R.id.cpiEditPostProgress)
        clPostFormLayout = view.findViewById(R.id.clPostFormLayout)

        btnCancel.setOnClickListener{
          Navigation.findNavController(view).popBackStack()
       }

        btnSubmit.setOnClickListener{
           submitPost()
        }

        postId = arguments?.getString("postId")
        postId?.let {
            cpiEditPostProgress.visibility = View.VISIBLE
            clPostFormLayout.visibility = View.GONE

            FirebaseFirestore.getInstance().collection("posts").document(it).get()
                .addOnSuccessListener { document ->
                    addTitle.setText(document.getString("title"))
                    addSubtitle.setText(document.getString("subtitle"))
                    addText.setText(document.getString("text"))

                    originalPostPicUrl = document.getString("postPic")
                    originalPostPicUrl?.let { url ->
                        if (url.trim() != "") {
                            Utils.setImageIntoView(postPicView, url, R.drawable.loading_pic)
                            postPicView.visibility = View.VISIBLE
                        }
                    }
                }
                .addOnFailureListener { err ->
                    Toast.makeText(context, "Error fetching post: ${err.message}", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(AddPostFragmentDirections.actionAddPostFragmentToProfileFragment())
                }
                .addOnCompleteListener {
                    clPostFormLayout.visibility = View.VISIBLE
                    cpiEditPostProgress.visibility = View.GONE
                }

        }

        return view
    }

    private fun onAddPictureButtonClicked(view: View) {

        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        galleryLauncher.launch(galleryIntent)
    }

    private fun submitPost() {
        val title = addTitle.text.toString().trim()
        val subtitle = addSubtitle.text.toString().trim()
        val text = addText.text.toString().trim()
        val postPic = postPicUrl ?: originalPostPicUrl ?: ""

        if (title.trim() == "" || subtitle.trim() == "" || text.trim() == "") {
            Toast.makeText(context, "Title, subtitle, and text cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        // Getting current user's ID as profileId
        val profile = GlobalProfileManager.getProfile()
        val profileRef = FirebaseFirestore.getInstance().collection("profiles").document(profile?.id ?: "")

        // Prepare the data
        val postData = hashMapOf(
            "profileId" to profileRef,
            "title" to title,
            "subtitle" to subtitle,
            "text" to text,
            "postPic" to postPic
        )

        btnSubmit.isEnabled = false
        btnCancel.isEnabled = false
        cpiAddPostProgress.visibility = View.VISIBLE

        if (postPic.trim() != ""  && postPic != originalPostPicUrl)
            uploadImageAndSavePost(postData)
        else
            savePost(postData)
    }

    private fun uploadImageAndSavePost(postData: HashMap<String, Any>) {
        val fileName = "images/${UUID.randomUUID()}.jpg" // Generates a unique file name for the image
        val storageRef = FirebaseStorage.getInstance().getReference(fileName)

        val imageUrl = postData["postPic"].toString()

        storageRef.putFile(Uri.parse(imageUrl))
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                    val fireImageUrl = uri.toString()
                    postData["postPic"] = fireImageUrl
                    savePost(postData)
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to upload image: ${it.message}", Toast.LENGTH_SHORT).show()
                btnSubmit.isEnabled = true
                btnCancel.isEnabled = true
                cpiAddPostProgress.visibility = View.GONE
            }
    }

    private fun savePost(postData: HashMap<String, Any>) {
        // Adding data to Fire-store
        val colReference = FirebaseFirestore.getInstance().collection("posts")
        val docReference = if (postId != null) colReference.document(postId!!).set(postData) else colReference.add(postData)

        docReference
            .addOnSuccessListener {
                Toast.makeText(context, "Post saved successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(AddPostFragmentDirections.actionAddPostFragmentToFeedFragment())
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error saving post: ${it.message}", Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener{
                btnSubmit.isEnabled = true
                btnCancel.isEnabled = true
                cpiAddPostProgress.visibility = View.GONE
            }
    }

}