package com.esgi.greenrepack

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.esgi.greenrepack.obj.Utilisateur
import com.esgi.greenrepack.services.ApiClient
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener {
            Log.i("aaa","aaaa")
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, R.string.form_login_error_missing_field, Toast.LENGTH_LONG).show()
            } else {
                login(Utilisateur(email, password))
            }
        }

        signInButton.setOnClickListener {
            startActivity(Intent(this, LoginAssociationActivity::class.java))
        }
    }

    private fun login(user: Utilisateur) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                Log.i("user", user.toString())
                Log.i("test","tyes")
                val response = ApiClient.apiService.login(user)

                if (response.isSuccessful && response.body() != null) {
                    val content = response.body()!!
                    Log.i("jwt",content.token)
                    val sharedPrefs = this@LoginActivity.getPreferences(Context.MODE_PRIVATE)
                    with (sharedPrefs.edit()) {
                        putString("jwt", content.token)
                        apply()
                    }
                    getAssoc()

                } else {
                    Log.e("Error Occured", response.message())
                }

            } catch (e: Exception) {
                Log.e("Exception Occured", e.message!!)
            }
        }
    }

    private fun getAssoc() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                Log.i("test","teeest")
                val sp = this@LoginActivity.getPreferences(Context.MODE_PRIVATE)
                val token: String = sp.getString("jwt", "null").toString()
                Log.i("token", token)
                val response = ApiClient.apiService.getAllAssociations("Bearer "+token)

                if (response.isSuccessful && response.body() != null) {
                    val content = response.body()
                    Log.i("content", content.toString())

                } else {
                    Log.e("Error Occured", response.message())
                }

            } catch (e: Exception) {
                Log.e("Exception Occured", e.message!!)
            }
        }
    }
}