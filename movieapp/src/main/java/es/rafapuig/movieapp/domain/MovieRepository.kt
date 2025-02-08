package es.rafapuig.movieapp.domain

import androidx.paging.PagingData
import es.rafapuig.movieapp.data.network.model.MovieResponse
import es.rafapuig.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun fetchMovies(): List<Movie>

    fun fetchMoviesFlow(): Flow<List<Movie>>

    fun fetchMoviesPagingFlow(): Flow<PagingData<Movie>>
}