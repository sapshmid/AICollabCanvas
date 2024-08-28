package com.example.aicollabcanvas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class ProfileFragment : Fragment() {

    var name: String? = null
    var role: String? = null
    var profileName: TextView? = null
    var profileRole: TextView? = null

    companion object {

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
        return view
    }

}
