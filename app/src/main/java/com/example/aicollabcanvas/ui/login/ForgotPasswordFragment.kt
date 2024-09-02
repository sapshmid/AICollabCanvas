package com.example.aicollabcanvas.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.aicollabcanvas.R

class ForgotPasswordFragment : Fragment() {

    private lateinit var etEnterYourEmail: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmNewPassword: EditText
    private lateinit var btnSubmit: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)

        etEnterYourEmail = view.findViewById(R.id.etEnterYourEmail)
        etNewPassword = view.findViewById(R.id.etNewPassword)
        etConfirmNewPassword = view.findViewById(R.id.etConfirmNewPassword)
        btnSubmit = view.findViewById(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            handleChangePassword()
        }

        return view
    }

    private fun handleChangePassword() {
        // Retrieve data from EditText fields
        val email = etEnterYourEmail.text.toString().trim()
        val newPassword = etNewPassword.text.toString()
        val confirmNewPassword = etConfirmNewPassword.text.toString()

        // Validate inputs
        if (email.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword != confirmNewPassword) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword.length < 6) {
            Toast.makeText(context, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
            return
        }

        // Proceed with updating the password
        updatePassword(email, newPassword)
    }

    private fun updatePassword(email: String, newPassword: String) {
        // This function should integrate with your backend or database logic
        // Example placeholder: You might call an API or update a local database record here

        // Simulating a success scenario
        Toast.makeText(context, "Password updated successfully!", Toast.LENGTH_SHORT).show()

        // If working with a backend, you would typically use Retrofit or another networking library
        // Example:
        // apiService.updatePassword(email, newPassword).enqueue(object : Callback<Response> { ... })
    }

}