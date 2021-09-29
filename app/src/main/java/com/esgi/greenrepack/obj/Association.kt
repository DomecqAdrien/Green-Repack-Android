package com.esgi.greenrepack.obj

data class Association (
    val id: Int = 0,
    val nom: String? = null,
    val email: String? = null,
    val RNA: String?= null,
    val password: String? = null,
    val logo: String,
    val project: List<Project>
)

