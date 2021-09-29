package com.esgi.greenrepack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_project.*
import kotlinx.android.synthetic.main.activity_register_association.*


class RegisterAssociationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_association)

        tvUploadImage.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type ="image/*"
                //registerForActivityResult(intent, 69)
                startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 69){
            imageView.setImageURI(data?.data) // handle chosen image
        }
    }
}