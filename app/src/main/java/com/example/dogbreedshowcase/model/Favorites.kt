package com.example.dogbreedshowcase.model

import java.io.Serializable

data class Favorites(
    val dogBreed: String,
    val favorite: Boolean
): Serializable
