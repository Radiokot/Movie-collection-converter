package ua.com.radiokot.mcc.imdb.rating.model

import ua.com.radiokot.mcc.base.rating.model.SimpleStarRating
import ua.com.radiokot.mcc.base.rating.model.StarRating
import ua.com.radiokot.mcc.imdb.movie.model.ImdbExistingMovie
import java.time.Instant

data class ImdbExistingMovieRating(
    val movie: ImdbExistingMovie,
    val rating: StarRating,
    val date: Instant
) {
    constructor(
        movie: ImdbExistingMovie,
        stars: Float,
        date: Instant
    ) : this(
        movie = movie,
        rating = SimpleStarRating(
            stars = stars,
            of = MAX_RATING_STARS
        ),
        date = date
    )

    companion object {
        const val MAX_RATING_STARS = 10
    }
}