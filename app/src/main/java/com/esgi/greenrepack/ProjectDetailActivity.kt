package com.esgi.greenrepack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.esgi.greenrepack.associations.SuccessActivity
import com.esgi.greenrepack.databinding.ActivityProjectBinding
import com.esgi.greenrepack.models.Association
import com.esgi.greenrepack.models.GreenCoin
import com.esgi.greenrepack.models.Investment
import com.esgi.greenrepack.models.Projet
import com.esgi.greenrepack.services.ApiClient
import com.esgi.greenrepack.services.TokenManager
import kotlinx.android.synthetic.main.activity_association_main_page.*
import kotlinx.android.synthetic.main.activity_project.*
import kotlinx.android.synthetic.main.activity_project.association_logo
import kotlinx.android.synthetic.main.activity_register_association.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate

class ProjectDetailActivity : AppCompatActivity() {

    private lateinit var tk: TokenManager
    private lateinit var projet: Projet
    private lateinit var association: String
    private lateinit var coins: List<GreenCoin>
    private lateinit var binding: ActivityProjectBinding
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectBinding.inflate(layoutInflater)
        tk = TokenManager(this)
        projet = intent.getParcelableExtra("projet")!!
        association = intent.getStringExtra("association").toString()
        getCoins()

    }

    private fun initView() {
        val view = binding.root
        setContentView(view)

        var remainingCoins = 0
        for(coin in coins) {
            remainingCoins += coin.montantRestant
        }

        with(binding) {
            projetAssociation.text = association
            projetNom.text = projet.libelle
            projetObjectif.text = projet.description
            projetMontant.text = "${projet.somme}€"
            projetDateDebut.text = projet.dateDebut.substring(0,10)
            projetDateFin.text = projet.dateFin.substring(0,10)
            tvRemainingCoins.text = String.format(getString(R.string.remaining_coins), remainingCoins)
            Glide.with(this@ProjectDetailActivity)
                .load(projet.logo)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(associationLogo)

            btnInvest.setOnClickListener {
                val amount = etInvestAmount.text.toString()
                when {
                    amount.isEmpty() -> displayError("Veuillez spécifier un montant")
                    amount.toInt() < 1 -> displayError("Le montant doit être supérieur à zéro")
                    amount.toInt() > remainingCoins -> displayError("Le montant doit inférieur à $remainingCoins")
                    else -> createInvestment(Investment(montant = amount.toInt(), projetId = projet.id!!))
                }
            }

            startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    result: ActivityResult ->
                if(result.resultCode == 100) {
                    etInvestAmount.text.clear()

                }
            }
        }
    }

    private fun displayError(message: String) {
        binding.tvInvestError.visibility = View.VISIBLE
        binding.tvInvestError.text = message
    }

    private fun createInvestment(investment: Investment) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = ApiClient.apiService.invest("Bearer ${tk.token}", investment)
                if (response.isSuccessful && response.body() != null) {
                    Log.i("response", response.body().toString())
                    val intent = Intent(this@ProjectDetailActivity, SuccessActivity::class.java)
                    intent.putExtra("text", "Merci pour votre investissement !")
                    startForResult.launch(intent)
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

    private fun getCoins() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = ApiClient.apiService.getGreenCoinsByUser("Bearer ${tk.token}")

                if (response.isSuccessful && response.body() != null) {
                    val content = response.body()
                    coins = content!!
                    initView()
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