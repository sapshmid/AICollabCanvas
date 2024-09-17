package com.example.aicollabcanvas.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.aicollabcanvas.R

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Initialize the button
        val btnRegisterHere: Button = view.findViewById(R.id.btnRegisterhere)
        val btnForgotPassword: Button = view.findViewById(R.id.btnForgotPassword)
        val btnSignin: Button = view.findViewById(R.id.btnSignin)

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
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_feedFragment)
        }

        return view
    }

  //  private fun navigateToRegisterFragment() {
        // Create an instance of RegisterFragment
    //    val registerFragment = RegisterFragment()

        // Perform the fragment transaction
        //requireActivity().supportFragmentManager.beginTransaction()
        //    .replace(R.id.fcMainLoginFragment, registerFragment) // Replace with the actual container id in your layout
        //    .addToBackStack(null) // Optional: Adds the transaction to the back stack
        //    .commit()
 //   }

 //   private fun navigateToForgotPasswordFragment() {
 //       val forgotPasswordFragment = ForgotPasswordFragment()
 //       requireActivity().supportFragmentManager.beginTransaction()
 //           .replace(R.id.fcMainLoginFragment, forgotPasswordFragment) // Replace with your container ID
 //           .addToBackStack(null)
 //           .commit()
    }
