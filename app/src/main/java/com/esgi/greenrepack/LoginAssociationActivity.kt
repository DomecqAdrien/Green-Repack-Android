package com.esgi.greenrepack

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.esgi.greenrepack.associations.InCurseActivity
import com.esgi.greenrepack.models.Utilisateur
import com.esgi.greenrepack.services.ApiClient
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginAssociationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_association)

        loginButton.setOnClickListener {
            Log.i("aaa","aaaa")
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, R.string.form_login_error_missing_field, Toast.LENGTH_LONG).show()
            } else {
                executeCall(Utilisateur(email, password))
            }
        }

        signInButton.setOnClickListener {
            startActivity(Intent(this, RegisterAssociationActivity::class.java))
        }
    }

    private fun executeCall(user: Utilisateur) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                Log.i("user", user.toString())
                Log.i("test","tyes")
                val response = ApiClient.apiService.loginAsAssociation(user)

                if (response.isSuccessful && response.body() != null) {
                    val content = response.body()!!
                    Log.i("jwt",content.token)
                    val sharedPrefs = this@LoginAssociationActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                    with (sharedPrefs.edit()) {
                        putString("jwt", content.token)
                        apply()
                    }
                    startActivity(Intent(this@LoginAssociationActivity, InCurseActivity::class.java))

                } else {
                    Log.e("Error Occured", response.message())
                }

            } catch (e: Exception) {
                Log.e("Exception Occured", e.message!!)
            }
        }
    }


}