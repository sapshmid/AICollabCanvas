package com.example.aicollabcanvas.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.aicollabcanvas.R

class RegisterFragment : Fragment() {
    private lateinit var etFirstname: EditText
    private lateinit var etLastname: EditText
    private lateinit var etAddEmail: EditText
    private lateinit var etAddPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var cbContributor: CheckBox
    private lateinit var cbCommunity: CheckBox
    private lateinit var btnRegister: Button

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
        cbContributor = view.findViewById(R.id.cbContibutor)
        cbCommunity = view.findViewById(R.id.cbCommunity)
        btnRegister = view.findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            registerNewMember()
        }

        return view
    }

    private fun registerNewMember() {
        // Retrieve data from views
        val firstName = etFirstname.text.toString().trim()
        val lastName = etLastname.text.toString().trim()
        val email = etAddEmail.text.toString().trim()
        val password = etAddPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()
        val isContributor = cbContributor.isChecked
        val isCommunityMember = cbCommunity.isChecked

        // Validate data
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            // Handle empty fields (e.g., show an error message)
            // Example: Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            // Handle password mismatch
            // Example: Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        // Proceed with saving the data (e.g., to a backend service or local database)
        // Example: saveUserToDatabase(firstName, lastName, email, password, isContributor, isCommunityMember)
    }


    // companion object {
   //     /**
   //      * Use this factory method to create a new instance of
   //      * this fragment using the provided parameters.
   //      *
   //      * @param param1 Parameter 1.
   //      * @param param2 Parameter 2.
   //      * @return A new instance of fragment RegisterFragment.
   //      */
   //     // TODO: Rename and change types and number of parameters
   //     @JvmStatic
   //     fun newInstance(param1: String, param2: String) =
   //         RegisterFragment().apply {
   //             arguments = Bundle().apply {
   //                 putString(ARG_PARAM1, param1)
   //                 putString(ARG_PARAM2, param2)
   //             }
   //         }
   // }
}