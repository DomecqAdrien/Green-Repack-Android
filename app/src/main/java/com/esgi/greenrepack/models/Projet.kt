package com.esgi.greenrepack.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Projet(
    var id: Int? = 0,
    var libelle: String,
    var description: String,
    var somme: Double,
    var dateDebut: String,
    var dateFin: String,
    var associationId: Int,
    var logo: String? = null
) : Parcelable
