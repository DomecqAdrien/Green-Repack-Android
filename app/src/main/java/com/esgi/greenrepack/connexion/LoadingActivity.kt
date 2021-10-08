package com.esgi.greenrepack.connexion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.esgi.greenrepack.ProjectListActivity
import com.esgi.greenrepack.associations.AssociationMainPageActivity
import com.esgi.greenrepack.databinding.ActivityLoadingBinding
import com.esgi.greenrepack.services.TokenManager

class LoadingActivity : AppCompatActivity() {

    private lateinit var tk: TokenManager
    private lateinit var binding: ActivityLoadingBinding
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tk = TokenManager(this)
        //tk.clear()

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            Log.i("resCode", result.resultCode.toString())
            if(result.resultCode == 80) {
                Log.i("resCode", "80")
                checkIsLogged()
            }
        }

        checkIsLogged()


    }

    private fun checkIsLogged() {
        Log.i("isLogged", tk.isLogged.toString())
        Log.i("isExpired", tk.isExpired.toString())
        if (tk.isLogged && !tk.isExpired) {
            Log.i("token", tk.token)
            Log.i("role", tk.role.toString())
            if(tk.role == "Association") {
                startActivity(Intent(this, AssociationMainPageActivity::class.java))
            }
            if(tk.role == "Utilisateur" || tk.role == "Marchand") {
                startActivity(Intent(this, ProjectListActivity::class.java))
            }
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}