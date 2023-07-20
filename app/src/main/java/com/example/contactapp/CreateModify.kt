package com.example.contactapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView


class CreateModify : AppCompatActivity() {

    //Creating Variables
    var btn_delete: Button? = null
    var btn_save: Button? = null
    var first_name: EditText? = null
    var last_name: EditText? = null
    var contact_number: EditText? = null
    var email: EditText? = null
    var address: EditText? = null
    var image: ImageView? = null
    var add_img: CardView? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_modify)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Initializing the buttons, CardView and EditTexts
        btn_delete = findViewById(R.id.btn_delete)
        btn_save = findViewById(R.id.btn_save)
        first_name = findViewById(R.id.first_name)
        last_name = findViewById(R.id.last_name)
        contact_number = findViewById(R.id.contact_number)
        email = findViewById(R.id.email)
        address = findViewById(R.id.address)
        image = findViewById(R.id.imgview)
        add_img = findViewById(R.id.add_img)

    }
    //menu to send back to the mainactivity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}