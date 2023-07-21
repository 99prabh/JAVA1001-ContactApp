package com.example.contactapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.contactapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Variable for View Binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for the floating action button to navigate to CreateModify activity
        binding.fab.setOnClickListener {
            // Start the CreateModify activity when the floating action button is clicked
            startActivity(Intent(this, CreateModify::class.java))
        }
    }

    // Create the options menu (to delete all items at once)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the main_menu XML file to create the options menu
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}
