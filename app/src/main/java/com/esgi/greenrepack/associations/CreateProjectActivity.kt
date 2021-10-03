package com.esgi.greenrepack.associations

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.esgi.greenrepack.R
import com.esgi.greenrepack.models.Projet
import com.esgi.greenrepack.services.ApiClient
import kotlinx.android.synthetic.main.activity_association_main_page.*
import kotlinx.android.synthetic.main.activity_project_create.*
import kotlinx.android.synthetic.main.activity_register_association.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class CreateProjectActivity : AppCompatActivity() {

    private var associationId: Int = 0
    private lateinit var associationLogo: String
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_create)

        associationId = intent.getIntExtra("associationId", 0)
        associationLogo = intent.getStringExtra("logo").toString()

        Glide.with(this)
            .load(associationLogo)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(association_logo_create)

        btn_create_project.setOnClickListener {
            if(
                projet_libelle.text.isEmpty() ||
                input_projet_description.text.isEmpty() ||
                input_projet_date_debut.text.isEmpty() ||
                input_projet_date_fin.text.isEmpty() ||
                input_projet_montant.text.isEmpty()
            ){
                displayError("Veuillez remplir tout les champs")
            } else {
                var projet = Projet(
                    libelle = projet_libelle.text.toString(),
                    description = input_projet_description.text.toString(),
                    somme = input_projet_montant.text.toString().toDouble(),
                    dateDebut = input_projet_date_debut.text.toString(),
                    dateFin = input_projet_date_fin.text.toString(),
                    associationId = associationId
                )
                Log.i("projet", projet.toString())
                createProjet(projet)
            }
        }

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if(result.resultCode == 100) {
                setResult(90)
                finish()
            }
        }
    }

    private fun goToSucces() {
        val intent = Intent(this, SuccessActivity::class.java)
        intent.putExtra("text", "Votre projet à bien été ajouté, les utilisateurs pouront effectuer des dons dès qu\\'il aura été validé par nos équipes.")
        startForResult.launch(intent)
    }



    fun showTimePickerDialog(v: View) {
       DatePickerFragment(v as TextView).show(supportFragmentManager, "datePicker")
    }

    private fun displayError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        tvLoginError.visibility = View.VISIBLE
        tvLoginError.text = message
    }

    private fun createProjet(projet: Projet) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val sp = this@CreateProjectActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                val token: String = sp.getString("jwt", "null").toString()
                val response = ApiClient.apiService.createProjet("Bearer $token", projet)

                if (response.isSuccessful && response.body() != null) {
                    goToSucces()
                } else {
                    Log.e("Error Occured", response.message())
                }

            } catch (e: Exception) {
                Log.e("Exception Occured", e.message!!)
            }
        }
    }
}

class DatePickerFragment(v: TextView) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val dateField = v

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(context!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val dayString = when {
            day < 10 -> "0$day"
            else -> day
        }
        val monthString = when {
            month < 10 -> "0$month"
            else -> month
        }
        dateField.text = "$year-$monthString-$dayString"
    }
}