package com.alexisdev.network.model

import com.google.gson.annotations.SerializedName

enum class GenreDto {
    @SerializedName("биография") BIOGRAPHY,
    @SerializedName("боевик") ACTION,
    @SerializedName("детектив") DETECTIVE,
    @SerializedName("драма") DRAMA,
    @SerializedName("комедия") COMEDY,
    @SerializedName("криминал") CRIME,
    @SerializedName("мелодрама") ROMANCE,
    @SerializedName("мюзикл") MUSICAL,
    @SerializedName("приключения") ADVENTURE,
    @SerializedName("триллер") THRILLER,
    @SerializedName("ужасы") HORROR,
    @SerializedName("фантастика") SCI_FI,
    @SerializedName("фэнтези") FANTASY
}
