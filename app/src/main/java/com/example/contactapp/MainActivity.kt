package com.example.contactapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    //Creating Variables
    private var fab: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initializing floating action button view
        fab = findViewById<FloatingActionButton>(R.id.fab)

        //send to AddorEditActivity when clicked on the floating action button
        fab?.setOnClickListener {
            val i = Intent(applicationContext, CreateModify::class.java)
            startActivity(i)
        }
    }

    //Crating the option menu to delete all the item at once
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

}