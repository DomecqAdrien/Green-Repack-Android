package com.esgi.greenrepack.obj

import java.util.*

data class Project(
    var id: Int = 0,
    var libelle: String? = null,
    var description: String? = null,
    var somme: Double? = null,
    var dateDebut: String? = null,
    var dateFin: String? = null,
    var associationId: Int = 0,
    var logo: String? = null
)
