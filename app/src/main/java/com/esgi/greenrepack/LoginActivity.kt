package com.esgi.greenrepack

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.esgi.greenrepack.models.Utilisateur
import com.esgi.greenrepack.services.ApiClient
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.tvLoginError
import kotlinx.android.synthetic.main.activity_register_association.*
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
                displayError(getText(R.string.form_login_error_missing_field).toString())
            } else {
                login(Utilisateur(email, password))
            }
        }

        signInButton.setOnClickListener {
            startActivity(Intent(this, LoginAssociationActivity::class.java))
        }
    }

    fun displayError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        tvLoginError.visibility = View.VISIBLE
        tvLoginError.text = message
    }

    private fun login(user: Utilisateur) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                Log.i("bbb","aaaa")
                val response = ApiClient.apiService.login(user)

                if (response.isSuccessful && response.body() != null) {
                    Log.i("ALLO","ALLO")
                    val content = response.body()!!
                    val sharedPrefs = this@LoginActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                    with (sharedPrefs.edit()) {
                        putString("jwt", content.token)
                        apply()
                    }
                    startActivity(Intent(this@LoginActivity, ProjectsActivity::class.java))

                } else {
                    Log.e("Error Occured", response.message())
                }

            } catch (e: Exception) {
                Log.e("Exception Occured", e.message!!)
            }
        }
    }

}