package com.alexisdev.film_details.model

data class FilmUi(
    val id: Int,
    val localizedName: String,
    val name: String,
    val year: Int,
    val rating: Double,
    val imageUrl: String?,
    val description: String?,
    val genres: List<GenreUi>
)