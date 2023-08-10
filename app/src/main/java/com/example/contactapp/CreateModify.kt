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

        //Perform different button click operations
        // this will save the data to the database also check if any data is null or not
        binding.btnSave.setOnClickListener {
            var success: Boolean = false
            val stream = ByteArrayOutputStream()

            // Checking if photo is null before compressing and saving it
            photo?.compress(Bitmap.CompressFormat.PNG, 100, stream)
            if(stream.size()==0){
                Toast.makeText(this, "Image is Required",Toast.LENGTH_SHORT).show()
            }else if(binding.firstName.text.toString().length==0||binding.lastName.text.toString().length==0||binding.contactNumber.text.toString().length==0||binding.email?.text.toString().length==0||binding.address?.text.toString().length==0){
                Toast.makeText(this, "All Fields Are Required",Toast.LENGTH_SHORT).show()
            }else{
                if (stream.toByteArray().size / ONE_MB_TO_KB > PREFERRED_IMAGE_SIZE) {
                    Toast.makeText(this, "Image Size has to be less then 1MB",Toast.LENGTH_SHORT).show()
                }else{
                    if (!isEditMode) {
                        val contact: Model = Model()
                        contact.first_name = binding.firstName.text.toString()
                        contact.last_name = binding.lastName.text.toString()
                        contact.contact_number = binding.contactNumber.text.toString()
                        contact.email = binding.email.text.toString()
                        contact.address = binding.address.text.toString()
                        contact.byteArray = stream.toByteArray()
                        success = dbHandler?.addContact(contact) as Boolean
                    } else {
                        val contact: Model = Model()
                        contact.id = intent.getIntExtra("Id", 0)
                        contact.first_name = binding.firstName.text.toString()
                        contact.last_name = binding.lastName.text.toString()
                        contact.contact_number = binding.contactNumber.text.toString()
                        contact.email = binding.email.text.toString()
                        contact.address = binding.address.text.toString()
                        contact.byteArray = stream.toByteArray()
                        success = dbHandler?.updateContact(contact) as Boolean
                    }
                }
            }
            if (success)
                finish()
        }
        //this will delete the current contact from database with a conformation dialog
        binding.btnDelete.setOnClickListener {
            val dialog = AlertDialog.Builder(this).setTitle("Info")
                .setMessage("Click 'YES' Delete the Contact.")
                .setPositiveButton("YES") { dialog, i ->
                    val success = dbHandler?.deleteContact(intent.getIntExtra("Id", 0)) as Boolean
                    if (success)
                        finish()
                    dialog.dismiss()
                }
                .setNegativeButton("NO") { dialog, i ->
                    dialog.dismiss()
                }
            dialog.show()
        }

        //this will pick image and open the gallery
        binding.addImg.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
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