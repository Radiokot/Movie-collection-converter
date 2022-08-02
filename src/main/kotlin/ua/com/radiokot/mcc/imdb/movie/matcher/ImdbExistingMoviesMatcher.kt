package ua.com.radiokot.mcc.imdb.movie.matcher

import ua.com.radiokot.mcc.base.movie.model.ExistingMovie
import ua.com.radiokot.mcc.base.movie.model.ExistingMovieMatch
import ua.com.radiokot.mcc.imdb.api.search.service.ImdbSearchService
import ua.com.radiokot.mcc.imdb.movie.model.ImdbExistingMovie

/**
 * Uses [ImdbSearchService] to match provided [ExistingMovie]s into [ImdbExistingMovie]
 */
class ImdbExistingMoviesMatcher(
    private val imdbSearchService: ImdbSearchService
) {
    fun match(movies: Collection<ExistingMovie>): Map<ExistingMovie, ExistingMovieMatch<ImdbExistingMovie>> {
        return movies
            .associateWith { movie ->
                val searchResults = imdbSearchService.search(movie.convenientName)

                val exactMatch = searchResults
                    .find {
                        it.name == movie.convenientName && it.releaseYear == movie.releaseYear
                    }

                // Maybe the name is different (differences in letters or special charachters),
                // but we can suggest the first result.
                val maybeMatch = searchResults
                    .firstOrNull()

                when {
                    exactMatch != null ->
                        ExistingMovieMatch.Found.Exact(ImdbExistingMovie(exactMatch))
                    maybeMatch != null ->
                        ExistingMovieMatch.Found.Maybe(ImdbExistingMovie(maybeMatch))
                    else ->
                        ExistingMovieMatch.NotFound()
                }
            }
    }
}