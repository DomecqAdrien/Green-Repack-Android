package com.esgi.greenrepack

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.esgi.greenrepack.obj.Association
import com.esgi.greenrepack.services.ApiClient
import kotlinx.android.synthetic.main.activity_project.*
import kotlinx.android.synthetic.main.activity_register_association.*
import kotlinx.android.synthetic.main.activity_register_association.tvLoginError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.util.*


class RegisterAssociationActivity : AppCompatActivity() {

    var byteImage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_association)


        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult  ->
                if(result.resultCode == Activity.RESULT_OK) {
                    val stream = contentResolver.openInputStream(result.data!!.data!!)
                    val data: ByteArray = stream!!.readBytes()
                    byteImage = Base64.getEncoder().encodeToString(data)
                    stream.close()
                }
        }

        tvUploadImage.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type ="image/*"
                startForResult.launch(intent)
        }

        btnRegister.setOnClickListener {
            if(etPassword.text.isEmpty() && etRNA.text.isEmpty() && etNom.text.isEmpty() && etEmail.text.isEmpty()){
                displayError("Veuillez remplir tout les champs")
            } else {
                with(this.byteImage) {
                    if (isNullOrBlank()) {
                        displayError("Veuillez Télécharger un logo")
                    } else {
                        var association = Association(
                            nom = etNom.text.toString(),
                            email = etEmail.text.toString(),
                            rna = etRNA.text.toString(),
                            password = etPassword.text.toString()
                        )
                        uploadImage(association, this)
                    }
                }
            }
        }
    }

    fun displayError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        tvLoginError.visibility = View.VISIBLE
        tvLoginError.text = message
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 69){
            imageView.setImageURI(data?.data) // handle chosen image
        }
    }



    private fun uploadImage(association: Association, image: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                var dataForm = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", image)
                    .build()
                val response = ApiClient.imageService.upload(dataForm)

                if (response.isSuccessful && response.body() != null) {
                    val content = response.body()!!
                    val url = content.getAsJsonObject("data").get("url").toString().replace("\"","")
                    Log.i("url", url)

                    association.logo = url
                    createAssociation(association);

                } else {
                    displayError("Error Occured")
                    Log.e("Error Occured", response.message())
                }
            } catch (e: Exception) {
                displayError("Exception Occured")
                Log.e("Exception Occured", e.message!!)
            }
        }
    }

    private fun createAssociation(association: Association) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = ApiClient.apiService.registerAssociation(association)
                Log.i("association", association.toString())
                Log.i("response", response.headers().toString())
                if (response.isSuccessful && response.body() != null) {
                    Log.i("response", response.body().toString())
                    val content = response.body()!!
                    val sharedPrefs = this@RegisterAssociationActivity.getPreferences(Context.MODE_PRIVATE)
                    with (sharedPrefs.edit()) {
                        putString("jwt", content.token)
                        apply()
                    }
                    startActivity(Intent(this@RegisterAssociationActivity, ProjectsActivity::class.java))
                } else {
                    displayError("Error Occured")
                    Log.e("Error Occured", response.toString())
                }
            } catch (e: Exception) {
                displayError("Exception Occured")
                Log.e("Exception Occured", e.message!!)
            }
        }
    }
}