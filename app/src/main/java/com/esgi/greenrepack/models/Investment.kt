package com.esgi.greenrepack.models

data class Investment(
    val id: Int? = 0,
    val montant: Int,
    val projetId: Int,
    val greenCoinId: Int? = 0
)