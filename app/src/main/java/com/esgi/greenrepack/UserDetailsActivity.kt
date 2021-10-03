package com.esgi.greenrepack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.greenrepack.adapter.GreenCoinAdapter
import com.esgi.greenrepack.databinding.ActivityUserAccountBinding
import com.esgi.greenrepack.models.GreenCoin
import com.esgi.greenrepack.services.ApiClient
import com.esgi.greenrepack.services.TokenManager
import kotlinx.android.synthetic.main.activity_project_list.*
import kotlinx.android.synthetic.main.activity_user_account.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var tk: TokenManager
    private lateinit var coins: List<GreenCoin>
    private lateinit var greenCoinAdapter: GreenCoinAdapter
    private lateinit var greenCoinRecyclerView: RecyclerView
    private lateinit var binding: ActivityUserAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAccountBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        tk = TokenManager(this)

        getCoins()
    }

    private fun initRecycler() {
        greenCoinRecyclerView = findViewById(R.id.rv_green_coins)
        greenCoinAdapter = GreenCoinAdapter(coins,this)
        greenCoinRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@UserDetailsActivity, RecyclerView.VERTICAL, false)
            adapter = greenCoinAdapter
        }
        Log.i("coins", coins.toString())
        greenCoinAdapter.coins = coins
        btn_user_logout.setOnClickListener {
            tk.clear()
            setResult(80)
            finish()
        }

    }

    private fun getCoins() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = ApiClient.apiService.getGreenCoinsByUser("Bearer ${tk.token}")

                if (response.isSuccessful && response.body() != null) {
                    val content = response.body()
                    coins = content!!
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