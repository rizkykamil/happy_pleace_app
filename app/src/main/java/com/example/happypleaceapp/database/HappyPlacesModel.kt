package com.example.happypleaceapp.database

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HappyPlacesModel(
        val id: Int,
        val title: String?,
        val image: String?,
        val date: String?,
        val description: String?,
        val location: String?,
        val latitude: Double,
        val longitude: Double
) : Parcelable