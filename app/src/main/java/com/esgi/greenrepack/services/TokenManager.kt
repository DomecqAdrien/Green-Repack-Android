package com.esgi.greenrepack.services

import android.content.Context
import android.util.Log
import com.auth0.android.jwt.JWT
import java.util.*

class TokenManager(context: Context) {

    var role: String? = null
    var isExpired: Boolean = false
    var isLogged: Boolean = false
    var token: String = ""
    var context: Context = context

    init {
        val sharedPrefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        token = sharedPrefs.getString("jwt", "").toString()
        Log.i("token", token)
        isLogged = token.isNotBlank()
        if(isLogged) {
            Log.i("islogged", "true")
            val jwtDecoder = JWT(token)
            val expiration = jwtDecoder.getClaim("exp").asLong()!!
            role = jwtDecoder.getClaim("role").asString().toString()
            isExpired = expiration < Date().time/1000
        }

    }

    fun storeToken(token: String) {
        val sharedPrefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        with (sharedPrefs.edit()) {
            putString("jwt", token)
            apply()
        }
    }

    fun clear() {
        val sharedPrefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            remove("jwt")
            apply()
        }
    }
}