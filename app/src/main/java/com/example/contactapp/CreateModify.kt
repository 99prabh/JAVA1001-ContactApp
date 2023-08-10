package com.example.contactapp

import Database
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.contactapp.databinding.CreateModifyBinding
import java.io.ByteArrayOutputStream

class CreateModify : AppCompatActivity() {

    private lateinit var binding: CreateModifyBinding
    private var dbHandler: Database? = null
    private var isEditMode = false
    private val pickImage = 100
    private var imageUri: Uri? = null
    private var photo: Bitmap? = null
    private val PREFERRED_IMAGE_SIZE = 1000
    private val ONE_MB_TO_KB = 1024

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CreateModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initializing the database
        dbHandler = Database(this)
        binding.btnDelete?.visibility = View.INVISIBLE
        //If activity is for edit then it will get data and load to the views also make the delete button visible
        if (intent.getStringExtra("Mode") == "E") {
            isEditMode = true
            val contact: Model = dbHandler!!.getContact(intent.getIntExtra("Id", 0))
            binding.firstName.setText(contact.first_name)
            binding.lastName.setText(contact.last_name)
            binding.contactNumber.setText(contact.contact_number)
            binding.email.setText(contact.email)
            binding.address.setText(contact.address)
            val by: ByteArray = contact.byteArray
            if(by.size==1){
                Log.e("Empty", contact.byteArray.toString())
            }else{
                val bmp: Bitmap = BitmapFactory.decodeByteArray(by, 0, by.size)
                binding.imgview.setImageBitmap(Bitmap.createScaledBitmap(bmp, 70, 70, false))
                photo = bmp
            }

            binding.btnDelete.visibility = View.VISIBLE

        }

    }


    //Get the image when selected from gallery
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.imgview.setImageURI(imageUri)
            photo = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
        }
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