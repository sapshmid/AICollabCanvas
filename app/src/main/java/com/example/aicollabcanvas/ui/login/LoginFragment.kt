package com.example.aicollabcanvas.ui.login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.aicollabcanvas.GlobalProfileManager
import com.example.aicollabcanvas.R
import com.example.aicollabcanvas.UserProfile
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.progressindicator.CircularProgressIndicator

class LoginFragment : Fragment() {

    private lateinit var btnSignin: Button
    private lateinit var etEmailAddress: EditText
    private lateinit var etPassword: EditText
    private lateinit var cpiLoginProgress: CircularProgressIndicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Initialize the button
        val btnRegisterHere: Button = view.findViewById(R.id.btnRegisterhere)
        val btnForgotPassword: Button = view.findViewById(R.id.btnForgotPassword)
        btnSignin = view.findViewById(R.id.btnSignin)
        etEmailAddress = view.findViewById(R.id.etEmailAddress)
        etPassword = view.findViewById(R.id.etPassword)
        cpiLoginProgress = view.findViewById(R.id.cpiLoginProgress)

        // Set up click listener to navigate to RegisterFragment
        btnRegisterHere.setOnClickListener {
          //  navigateToRegisterFragment()
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment3)
        }

        btnForgotPassword.setOnClickListener {
          //  navigateToForgotPasswordFragment()
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        btnSignin.setOnClickListener {
            performLogin()
        }

        return view
    }

    private fun performLogin() {
        val email = etEmailAddress.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        btnSignin.isEnabled = false
        cpiLoginProgress.visibility = View.VISIBLE

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fetchUserProfile()
                } else {
                    Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    btnSignin.isEnabled = true
                    cpiLoginProgress.visibility = View.GONE
                }
            }
    }

    private fun fetchUserProfile() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseFirestore.getInstance().collection("profiles").document(userId).get()
                .addOnSuccessListener { document ->
                    val userProfile = UserProfile(
                        id = userId,
                        name = document.getString("name") ?: "",
                        role = document.getString("role") ?: "",
                        profilePic =  document.getString("profilePic") ?: ""
                    )
                    GlobalProfileManager.setProfile(userProfile)
                    findNavController().navigate(R.id.action_loginFragment_to_feedFragment)
                    activity?.let { activity ->
                        val bottomNav = activity.findViewById<BottomNavigationView>(R.id.mainActivityBottomNavigationView)
                        bottomNav.visibility = View.VISIBLE
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to fetch profile: ${it.message}", Toast.LENGTH_SHORT).show()
                }.addOnCompleteListener{
                    btnSignin.isEnabled = true
                    cpiLoginProgress.visibility = View.GONE
                }
        }
    }
}
