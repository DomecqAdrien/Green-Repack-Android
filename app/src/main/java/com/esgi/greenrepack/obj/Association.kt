package com.esgi.greenrepack.obj

data class Association (
    val id: Int? = 0,
    val nom: String? = null,
    val email: String? = null,
    val rna: String?= null,
    val password: String? = null,
    var logo: String? = null,
    var project: List<Project>? = null
)

