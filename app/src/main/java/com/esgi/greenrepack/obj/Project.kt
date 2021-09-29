package com.esgi.greenrepack.obj

import java.util.*

data class Project(
    var id: Int = 0,
    var libelle: String? = null,
    var description: String? = null,
    var somme: Double? = null,
    var dateDebut: Date? = null,
    var dateFin: Date? = null,
    var associationId: Int = 0
)
