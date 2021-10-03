package com.esgi.greenrepack.associations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esgi.greenrepack.R
import kotlinx.android.synthetic.main.activity_create_success.*

class SuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_success)

        val text = intent.getStringExtra("text").toString()
        success_text.text = text

        success_btn_back.setOnClickListener {
            setResult(100)
            finish()
        }
    }
}