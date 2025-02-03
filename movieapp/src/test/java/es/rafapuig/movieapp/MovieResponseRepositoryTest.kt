package es.rafapuig.movieapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.rafapuig.movieapp.data.MovieRepositoryImpl
import es.rafapuig.movieapp.data.network.api.MovieService
import es.rafapuig.movieapp.data.network.model.MovieResponse
import es.rafapuig.movieapp.data.network.model.MoviesResponse
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class MovieResponseRepositoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun fetchMovies() {
        val movies = listOf(MovieResponse(id = 3), MovieResponse(id = 4))
        val response = MoviesResponse(1, movies)

        val movieService: MovieService = mock {
            onBlocking { getMovies(anyString()) } doReturn
                    response
        }

        val movieRepository = MovieRepositoryImpl(movieService)

        runTest {
            val movies2 = movieRepository.fetchMovies()
            val movieLiveData = movies2

        }
    }
}