package com.example.aicollabcanvas

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostMain) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView: BottomNavigationView =
            findViewById(R.id.mainActivityBottomNavigationView)
        navController.let { NavigationUI.setupWithNavController(bottomNavigationView, it) }


        val auth = FirebaseAuth.getInstance()

        bottomNavigationView.setOnItemSelectedListener { item ->
            val destination = navController.currentDestination?.id
            destination?.let {
                if (destination != item.itemId) {
                    navController.popBackStack(item.itemId, false)
                    navController.navigate(item.itemId)
                }
            }

            if (item.itemId == R.id.loginFragment) {
                auth.signOut()
                GlobalProfileManager.clearProfile()
                bottomNavigationView.visibility = View.GONE
            }

            true
        }


        // Initialize in a signed-out mode
        auth.signOut()
        bottomNavigationView.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().signOut() // Log out when the app is stopped.
    }
}
