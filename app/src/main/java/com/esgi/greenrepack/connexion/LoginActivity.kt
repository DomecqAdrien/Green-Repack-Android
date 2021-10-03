package com.esgi.greenrepack.connexion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.esgi.greenrepack.services.TokenManager
import com.esgi.greenrepack.ProjectListActivity
import com.esgi.greenrepack.R
import com.esgi.greenrepack.associations.AssociationMainPageActivity
import com.esgi.greenrepack.models.Utilisateur
import com.esgi.greenrepack.services.ApiClient
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.tvLoginError
import kotlinx.android.synthetic.main.activity_register_association.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {


    private lateinit var tk: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tk = TokenManager(this)

        setContentView(R.layout.activity_login)

        btn_login_association.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                displayError(getText(R.string.form_login_error_missing_field).toString())
            } else {
                login(Utilisateur(email, password))
            }
        }

        btn_inscription.setOnClickListener {
            startActivity(Intent(this, LoginAssociationActivity::class.java))
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    private fun displayError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        tvLoginError.visibility = View.VISIBLE
        tvLoginError.text = message
    }

    private fun login(user: Utilisateur) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = ApiClient.apiService.login(user)

                if (response.isSuccessful && response.body() != null) {
                    val content = response.body()!!
                    tk.storeToken(content.token)
                    startActivity(Intent(this@LoginActivity, ProjectListActivity::class.java))
                } else {
                    Log.e("Error Occured", response.message())
                }

            } catch (e: Exception) {
                Log.e("Exception Occured", e.message!!)
            }
        }
    }

}