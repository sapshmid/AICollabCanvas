package com.example.aicollabcanvas.ui.login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.aicollabcanvas.R
import com.google.android.material.progressindicator.CircularProgressIndicator

class RegisterFragment : Fragment() {
    private lateinit var etFirstname: EditText
    private lateinit var etLastname: EditText
    private lateinit var etAddEmail: EditText
    private lateinit var etAddPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var rgRole: RadioGroup
    private lateinit var btnRegister: Button
    private lateinit var cpiRegisterProgress: CircularProgressIndicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        etFirstname = view.findViewById(R.id.etFirstname)
        etLastname = view.findViewById(R.id.etLastname)
        etAddEmail = view.findViewById(R.id.etAddEmail)
        etAddPassword = view.findViewById(R.id.etAddPassword)
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword)
        rgRole = view.findViewById(R.id.rgRegisterRole)
        btnRegister = view.findViewById(R.id.btnRegister)
        cpiRegisterProgress = view.findViewById(R.id.cpiRegisterProgress)

        btnRegister.setOnClickListener {
            registerNewMember()
        }

        return view
    }


    fun getCurrentRole(): String {
        return when (rgRole.checkedRadioButtonId) {
            R.id.rbRegisterCommunity -> "Community"
            R.id.rbRegisterContributor -> "Contributor"
            else -> "No selection"
        }
    }

    private fun registerNewMember() {

        // Retrieve data from views
        val firstName = etFirstname.text.toString().trim()
        val lastName = etLastname.text.toString().trim()
        val email = etAddEmail.text.toString().trim()
        val password = etAddPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()
        val role = getCurrentRole()

        // Validate data
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        btnRegister.isEnabled = false;
        cpiRegisterProgress.visibility = View.VISIBLE

        // Proceed with saving the data (e.g., to a backend service or local database)
        // Firebase Authentication to register user
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = task.result?.user
                    if (firebaseUser != null) {
                        saveUserProfile(firebaseUser.uid, firstName, lastName, role);
                    } else {
                        btnRegister.isEnabled = true
                        cpiRegisterProgress.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(context, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    btnRegister.isEnabled = true
                    cpiRegisterProgress.visibility = View.GONE
                }
            }

    }

    private fun saveUserProfile(userId: String, firstName: String, lastName: String, role: String) {
        val userProfile = hashMapOf(
            "name" to "$firstName $lastName",
            "role" to "$role", // Modify as needed
            "profilePic" to ""
        )
        FirebaseFirestore.getInstance().collection("profiles").document(userId).set(userProfile)
            .addOnSuccessListener {
                Toast.makeText(context, "Profile created successfully", Toast.LENGTH_SHORT).show()
                // Navigate to another fragment or activity as needed
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to create profile: ${it.message}", Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener {
                btnRegister.isEnabled = true
                cpiRegisterProgress.visibility = View.GONE
            }
    }

}