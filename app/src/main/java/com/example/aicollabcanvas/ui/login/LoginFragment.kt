package com.example.aicollabcanvas.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

        // Set up click listener to navigate to RegisterFragment
        btnRegisterHere.setOnClickListener {
            navigateToRegisterFragment()
        }
        return view
    }

    private fun navigateToRegisterFragment() {
        // Create an instance of RegisterFragment
        val registerFragment = RegisterFragment()

        // Perform the fragment transaction
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fcMainLoginFragment, registerFragment) // Replace with the actual container id in your layout
            .addToBackStack(null) // Optional: Adds the transaction to the back stack
            .commit()
    }


   // companion object {
   //     /**
   //      * Use this factory method to create a new instance of
   //      * this fragment using the provided parameters.
   //      *
   //      * @param param1 Parameter 1.
   //      * @param param2 Parameter 2.
   //      * @return A new instance of fragment LoginFragment.
   //      */
   //     // TODO: Rename and change types and number of parameters
   //     @JvmStatic
   //     fun newInstance(param1: String, param2: String) =
   //         LoginFragment().apply {
   //             arguments = Bundle().apply {
   //                 putString(ARG_PARAM1, param1)
   //                 putString(ARG_PARAM2, param2)
   //             }
   //         }
   // }
}