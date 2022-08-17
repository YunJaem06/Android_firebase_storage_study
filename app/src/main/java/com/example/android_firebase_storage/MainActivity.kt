package com.example.android_firebase_storage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.android_firebase_storage.databinding.ActivityMainBinding
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var ImageUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainFirebaseImg.setOnClickListener {

            selectImage()
        }
        binding.btnMainImgUploading.setOnClickListener {

            uploadImage()
        }
    }

    private fun uploadImage() {

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)

        val storageReference = FirebaseStorage.getInstance().reference.child("image/$fileName")

        storageReference.putFile(ImageUri)
            .addOnSuccessListener {

                binding.mainFirebaseImg.setImageURI(null)
                Toast.makeText(this@MainActivity, "업로드 성공", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this@MainActivity, "업로드 실패", Toast.LENGTH_SHORT).show()
            }

    }

    private fun selectImage() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK){

            ImageUri = data?.data!!
            binding.mainFirebaseImg.setImageURI(ImageUri)
        }
    }
}