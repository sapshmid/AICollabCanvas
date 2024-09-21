package com.example.aicollabcanvas

import PostAdapter
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.firebase.appcheck.internal.util.Logger
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProfileFragment : Fragment() {

    var profileName: TextView? = null
    var profileRole: TextView? = null
    var profilePic: ImageView? = null

    var editName: EditText? = null
    var editRole: RadioGroup? = null
    var editProfileButton: ImageButton? = null
    var editPictureButton: ImageButton? = null

    var editedPic: String? = null

    lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    var saveProfileEditButton: ImageButton? = null
    var cancelProfileEditButton: ImageButton? = null

    var recyclerView: RecyclerView? = null

    var cpiProfileProgress: CircularProgressIndicator? = null
    var cpiProfileFeedProgress: CircularProgressIndicator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    profilePic?.setImageURI(it)
                    editedPic = it.toString()
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
        profileRole = view.findViewById(R.id.tvProfileRole)
        profilePic = view.findViewById(R.id.ivProfilePic)
        showProfile()


        editName = view.findViewById(R.id.etEditName)
        editRole = view.findViewById(R.id.rgRole)
        editProfileButton = view.findViewById(R.id.ibtnEditProfileButton)
        editProfileButton?.setOnClickListener(::onEditProfileButtonClicked)

        editPictureButton = view.findViewById(R.id.ibtnEditPictureButton)
        editPictureButton?.setOnClickListener(::onEditPictureButtonClicked)

        saveProfileEditButton = view.findViewById(R.id.ibtnSaveButton)
        saveProfileEditButton?.setOnClickListener(::onSaveEditButtonClicked)

        cancelProfileEditButton = view.findViewById(R.id.ibtnCancelButton)
        cancelProfileEditButton?.setOnClickListener(::onCancelEditButtonClicked)

        recyclerView = view.findViewById(R.id.rvProfilePostsContainer)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        cpiProfileProgress = view.findViewById(R.id.cpiProfileProgress)
        cpiProfileFeedProgress = view.findViewById(R.id.cpiProfileFeedProgress)

        loadUserPosts()
        return view
    }

    private fun showProfile() {
        val profile = GlobalProfileManager.getProfile()
        profileName?.text = profile?.name
        profileRole?.text = profile?.role
        setProfilePic(profile?.profilePic)
    }

    private fun setProfilePic(imageUri: String?) {
        profilePic?.let {
            Utils.setImageIntoView(it, imageUri, R.drawable.empty_profile)
        }
    }

    fun onEditProfileButtonClicked(view: View) {

        editName?.setText(profileName?.text)
        setCurrentRole(profileRole?.text.toString())
        editedPic = GlobalProfileManager.getProfile()?.profilePic

        toggleEditMode(true)
    }

    fun onEditPictureButtonClicked(view: View) {

        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        galleryLauncher.launch(galleryIntent)

    }

    fun onSaveEditButtonClicked(view: View) {

        val prevProfile = GlobalProfileManager.getProfile()
        if (prevProfile != null) {
            val name = editName?.text.toString()
            val role = getCurrentRole()
            val profilePic = editedPic

            if (name.trim() == "") {
                Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                return
            }

            cpiProfileProgress?.visibility = View.VISIBLE
            saveProfileEditButton?.isEnabled = false

            if (profilePic != prevProfile.profilePic)
                uploadImageAndSaveProfile(prevProfile.id, profilePic, name, role)
            else if (name != prevProfile.name || role != prevProfile.role)
                saveUserProfile(prevProfile.id, name, role, profilePic)
            else {
                cpiProfileProgress?.visibility = View.GONE
                saveProfileEditButton?.isEnabled = true
                toggleEditMode(false)
            }
        }
        else toggleEditMode(false)
    }

    fun onCancelEditButtonClicked(view: View) {
        showProfile()
        toggleEditMode(false)
    }

    fun toggleEditMode(isEditMode: Boolean) {
        profileName?.visibility = if (isEditMode) View.GONE else View.VISIBLE
        profileRole?.visibility = if (isEditMode) View.GONE else View.VISIBLE
        editProfileButton?.visibility = if (isEditMode) View.GONE else View.VISIBLE
        recyclerView?.visibility = if (isEditMode) View.GONE else View.VISIBLE

        editName?.visibility = if (!isEditMode) View.GONE else View.VISIBLE
        editRole?.visibility = if (!isEditMode) View.GONE else View.VISIBLE
        editPictureButton?.visibility = if (!isEditMode) View.GONE else View.VISIBLE

        saveProfileEditButton?.visibility = if (!isEditMode) View.GONE else View.VISIBLE
        cancelProfileEditButton?.visibility = if (!isEditMode) View.GONE else View.VISIBLE
    }

    fun getCurrentRole(): String {
        return when (editRole?.checkedRadioButtonId) {
            R.id.rbCommunity -> "Community"
            R.id.rbContributor -> "Contributor"
            else -> "No selection"
        }
    }

    fun setCurrentRole(currentRole: String) {
        if (currentRole == "Community")
            editRole?.check(R.id.rbCommunity)
        else
            editRole?.check(R.id.rbContributor)
    }

    private fun uploadImageAndSaveProfile(userId: String, imageUrl: String?, name: String, role: String) {
        val storageRef = FirebaseStorage.getInstance().reference.child("profile_images/$userId.jpg")
        storageRef.putFile(Uri.parse(imageUrl))
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                    val fireImageUrl = uri.toString()
                    saveUserProfile(userId, name, role, fireImageUrl)
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to upload image: ${it.message}", Toast.LENGTH_SHORT).show()
                cpiProfileProgress?.visibility = View.GONE
                saveProfileEditButton?.isEnabled = true
            }
    }

    private fun saveUserProfile(userId: String, name: String, role: String, imageUrl: String?) {
        val userProfile = hashMapOf(
            "name" to "$name",
            "role" to "$role",
            "profilePic" to imageUrl
        )
        FirebaseFirestore.getInstance().collection("profiles").document(userId).set(userProfile)
            .addOnSuccessListener {
                GlobalProfileManager.setProfile(UserProfile(
                    id = userId,
                    name = name,
                    role = role,
                    profilePic = imageUrl ?: ""
                ))
                Toast.makeText(context, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to create profile: ${it.message}", Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener {
                cpiProfileProgress?.visibility = View.GONE
                saveProfileEditButton?.isEnabled = true
                showProfile()
                toggleEditMode(false)
            }
    }

    private fun loadUserPosts() {
        val userId = GlobalProfileManager.getProfile()?.id
        val profileRef = FirebaseFirestore.getInstance().collection("profiles").document(userId ?: "")

        cpiProfileFeedProgress?.visibility = View.VISIBLE
        FirebaseFirestore.getInstance().collection("posts")
            .whereEqualTo("profileId", profileRef)
            .get()
            .addOnSuccessListener { result ->
                val posts = result.documents.map { document ->
                    // Convert each document to a Post object
                    document.toObject(Post::class.java)?.apply {
                        id = document.id  // Set the ID of the Post object
                        profile = GlobalProfileManager.getProfile()  // Set the associated profile
                    }
                }.filterNotNull()
                setupRecyclerView(posts.toMutableList())
                cpiProfileFeedProgress?.visibility = View.GONE
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error fetching posts: ${it.message}", Toast.LENGTH_SHORT).show()
                cpiProfileFeedProgress?.visibility = View.GONE
            }
    }

    private fun setupRecyclerView(posts: MutableList<Post>) {
        lateinit var adapter: PostAdapter
        adapter = PostAdapter(posts, object : PostAdapter.OnPostInteractionListener {
            override fun onDeletePost(position: Int) {
                deletePost(posts, position, adapter)
            }

            override fun onEditPost(position: Int) {
                val action = ProfileFragmentDirections.actionProfileFragmentToAddPostFragment(posts[position].id)
                findNavController().navigate(action)
            }
        }, true)
        recyclerView?.adapter = adapter
    }

    private fun deletePost(posts: MutableList<Post>, position: Int, adapter: PostAdapter) {
        FirebaseFirestore.getInstance().collection("posts").document(posts[position].id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(context, "Post deleted successfully", Toast.LENGTH_SHORT).show()
                posts.removeAt(position)
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error deleting post: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }


}
