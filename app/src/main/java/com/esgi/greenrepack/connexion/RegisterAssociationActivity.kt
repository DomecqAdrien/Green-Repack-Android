package com.esgi.greenrepack.connexion

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.esgi.greenrepack.ProjectListActivity
import com.esgi.greenrepack.R
import com.esgi.greenrepack.associations.AssociationMainPageActivity
import com.esgi.greenrepack.models.Association
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
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_association)


        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult  ->
                if(result.resultCode == Activity.RESULT_OK) {
                    val stream = contentResolver.openInputStream(result.data!!.data!!)
                    val data: ByteArray = stream!!.readBytes()
                    byteImage = Base64.getEncoder().encodeToString(data)
                    stream.close()
                }
                if(result.resultCode == 80) {
                    finish()
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
                        displayError("Veuillez T??l??charger un logo")
                    } else {
                        val association = Association(
                            nom = etNom.text.toString(),
                            email = etEmail.text.toString(),
                            rna = etRNA.text.toString(),
                            password = etPassword.text.toString(),
                            projets = listOf()
                        )
                        uploadImage(association, this)
                    }
                }
            }
        }
    }

    private fun displayError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        tvLoginError.visibility = View.VISIBLE
        tvLoginError.text = message
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
                    val sharedPrefs = this@RegisterAssociationActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                    with (sharedPrefs.edit()) {
                        putString("jwt", content.token)
                        apply()
                    }
                    startForResult.launch(Intent(this@RegisterAssociationActivity, AssociationMainPageActivity::class.java))
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