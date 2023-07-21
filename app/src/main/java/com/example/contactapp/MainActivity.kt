package com.example.contactapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.contactapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Creating Variable
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for the floating action button to navigate to CreateModify activity
        binding.fab.setOnClickListener {
            startActivity(Intent(this, CreateModify::class.java))
        }
    }

    // Creating the option menu to delete all the item at once
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}
