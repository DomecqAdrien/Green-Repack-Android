package com.esgi.greenrepack.models

import java.util.*

data class GreenCoin (
    var montant: Int,
    var montantRestant: Int,
    var dateExpiration: String,
    var utilisateurId: Int
)
