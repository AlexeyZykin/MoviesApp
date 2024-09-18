package com.alexisdev.network.model

import com.google.gson.annotations.SerializedName

data class FilmsResponseDto(
    @SerializedName("films") val films: List<FilmDto>
)
