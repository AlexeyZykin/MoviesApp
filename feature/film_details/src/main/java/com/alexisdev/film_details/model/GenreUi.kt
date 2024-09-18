package com.alexisdev.film_details.model

import androidx.annotation.StringRes
import com.alexisdev.film_details.R

enum class GenreUi(
    @StringRes val title: Int
) {
    BIOGRAPHY(title = R.string.genre_biography_title),
    ACTION(title = R.string.genre_action_title),
    DETECTIVE(title = R.string.genre_detective_title),
    DRAMA(title = R.string.genre_drama_title),
    COMEDY(title = R.string.genre_comedy_title),
    CRIME(title = R.string.genre_crime_title),
    ROMANCE(title = R.string.genre_romance_title),
    MUSICAL(title = R.string.genre_musical_title),
    ADVENTURE(title = R.string.genre_adventure_title),
    THRILLER(title = R.string.genre_thriller_title),
    HORROR(title = R.string.genre_horror_title),
    SCI_FI(title = R.string.genre_sci_fi_title),
    FANTASY(title = R.string.genre_fantasy_title)
}