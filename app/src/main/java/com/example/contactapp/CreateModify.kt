package com.example.contactapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.contactapp.databinding.CreateModifyBinding

class CreateModify : AppCompatActivity() {

    // Variable for View Binding
    private lateinit var binding: CreateModifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using View Binding
        binding = CreateModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enable the "Up" button in the action bar to navigate back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Handle menu item selection (in this case, the "Up" button to navigate back)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Get the ID of the selected menu item
        val id = item.itemId

        // Check if the "Up" button is clicked
        if (id == android.R.id.home) {
            // Finish the current activity and return to the previous activity (MainActivity)
            finish()
            return true
        }

        // For other menu items, use the default behavior
        return super.onOptionsItemSelected(item)
    }
}
