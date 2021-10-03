package com.esgi.greenrepack.models

data class Association (
    val id: Int? = 0,
    val nom: String,
    val email: String,
    val rna: String?= null,
    val password: String? = null,
    var logo: String? = null,
    var projets: List<Projet>
)

