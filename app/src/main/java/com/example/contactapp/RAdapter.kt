package com.example.contactapp
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.databinding.RvItemsBinding

class RAdapter(private val contactList: List<Model>, private val context: Context) : RecyclerView.Adapter<RAdapter.ContactViewHolder>() {

    //view holder to set the xml file
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = RvItemsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ContactViewHolder(binding)
    }

    //Binding the view holder with the views and setting the texts and images
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.binding.name.text = "${contact.first_name} ${contact.last_name}"
        holder.binding.contact.text = contact.contact_number
        holder.binding.email.text = contact.email
        holder.binding.address.text = contact.address
        Log.e("ASS", contact.byteArray.toString())
        val by: ByteArray = contact.byteArray
        if (by.size == 1) {
            Log.e("Empty", contact.byteArray.toString())
        } else {
            val bmp: Bitmap = BitmapFactory.decodeByteArray(by, 0, by.size)
            holder.binding.imgview.setImageBitmap(Bitmap.createScaledBitmap(bmp, 70, 70, false))
        }

        holder.binding.c.setOnClickListener {
            val i = Intent(context, CreateModify::class.java)
            i.putExtra("Mode", "E")
            i.putExtra("Id", contact.id)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }

    //getting the item count on the list
    override fun getItemCount(): Int {
        return contactList.size
    }

    //initializing the view holder
    inner class ContactViewHolder(val binding: RvItemsBinding) : RecyclerView.ViewHolder(binding.root)
}
