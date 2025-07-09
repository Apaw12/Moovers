package com.example.moovers.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val title: String,
    val releaseDate: String,
    val rating: Float,
    val posterResId: Int,
    val director: String,
    val synopsis: String
) : Parcelable
