package es.rafapuig.movieapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.rafapuig.movieapp.data.MovieRepository
import es.rafapuig.movieapp.data.network.api.MovieService
import es.rafapuig.movieapp.data.network.model.Movie
import es.rafapuig.movieapp.data.network.model.MoviesResponse
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class MovieRepositoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun fetchMovies() {
        val movies = listOf(Movie(id = 3), Movie(id = 4))
        val response = MoviesResponse(1, movies)

        val movieService: MovieService = mock {
            onBlocking { getMovies(anyString()) } doReturn
                    response
        }

        val movieRepository = MovieRepository(movieService)

        runTest {
            val movies2 = movieRepository.fetchMovies()
            val movieLiveData = movies2

        }
    }
}