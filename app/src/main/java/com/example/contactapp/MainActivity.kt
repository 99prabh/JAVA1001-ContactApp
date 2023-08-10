package com.example.contactapp

import Database
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp.databinding.ActivityMainBinding
import com.example.contactapp.databinding.ContentMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contentBinding: ContentMainBinding
    private lateinit var rAdapter: RAdapter
    private lateinit var dbHandler: Database
    private lateinit var listcontacts: List<Model>
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing everything
        linearLayoutManager = LinearLayoutManager(applicationContext)

        // Accessing RecyclerView using binding instance for content_main.xml
        contentBinding = ContentMainBinding.bind(binding.root)
        contentBinding.recyclerView.layoutManager = linearLayoutManager

        // Send to AddorEditActivity when clicked on the floating action button
        binding.fab.setOnClickListener {
            val i = Intent(applicationContext, CreateModify::class.java)
            startActivity(i)
        }

        // Initializing the database and getting all the contacts and feeding them to the adapter
        dbHandler = Database(this)
        listcontacts = dbHandler.contact()
        rAdapter = RAdapter(listcontacts, applicationContext)
        contentBinding.recyclerView.adapter = rAdapter

    }

    // Creating the option menu to delete all items at once
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu.findItem(R.id.searchContact)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    searchContact(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    searchContact(newText)
                } else {
                    resetContactList()
                }
                return true
            }
        })

        return true
    }

    private fun resetContactList() {
        listcontacts = dbHandler.contact()
        rAdapter.updateContacts(listcontacts)
    }

    private fun searchContact(query: String) {
        val matchingContacts = listcontacts.filter { contact ->
            val fullName = "${contact.first_name} ${contact.last_name}"
            contact.first_name.contains(query, ignoreCase = true) ||
                    contact.last_name.contains(query, ignoreCase = true) ||
                    fullName.contains(query, ignoreCase = true)
        }
        rAdapter.updateContacts(matchingContacts)
    }

}
