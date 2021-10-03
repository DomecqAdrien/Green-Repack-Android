package com.esgi.greenrepack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.greenrepack.adapter.AssociationAdapter
import com.esgi.greenrepack.models.Association
import com.esgi.greenrepack.services.ApiClient
import com.esgi.greenrepack.services.TokenManager
import kotlinx.android.synthetic.main.activity_project_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ProjectListActivity : AppCompatActivity() {

    private lateinit var tk: TokenManager
    private lateinit var associations: List<Association>
    private lateinit var associationRecyclerView: RecyclerView
    private lateinit var associationAdapter : AssociationAdapter
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        tk = TokenManager(this)
        getAssociations()


        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if(result.resultCode == 80) {
                setResult(80)
                finish()
            }
        }

    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Quitter Green Repack")
        builder.setMessage("Voulez vous vraiment quitter l'application ?")
        builder.setPositiveButton("Quitter") { _, _ ->
            finishAffinity()
        }
        builder.setNegativeButton("Annuler") { _, _ ->
        }
        builder.show()
    }

    private fun initRecycler() {
        setContentView(R.layout.activity_project_list)
        associationRecyclerView = findViewById(R.id.associationRecyclerView)
        associationAdapter = AssociationAdapter(this)
        associationRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ProjectListActivity, RecyclerView.VERTICAL, false)
            adapter = associationAdapter
        }
        Log.i("asso", associations.toString())
        associationAdapter.associations = associations.filter{ it.projets.isNotEmpty() }

        btn_account.setOnClickListener {
            startForResult.launch(Intent(this, UserDetailsActivity::class.java))
        }
    }

    private fun getAssociations() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = ApiClient.apiService.getAllAssociations("Bearer ${tk.token}")

                if (response.isSuccessful && response.body() != null) {
                    val content = response.body()
                    associations = content!!
                    initRecycler()
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