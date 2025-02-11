package es.rafapuig.movieapp

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.rafapuig.movieapp.data.MovieRepositoryImpl
import es.rafapuig.movieapp.data.network.api.MovieService
import es.rafapuig.movieapp.data.network.model.MovieResponse
import es.rafapuig.movieapp.data.network.model.MoviesResponse
import es.rafapuig.movieapp.data.network.provider.MovieNetworkProvider
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
        /*val movies = listOf(MovieResponse(id = 3), MovieResponse(id = 4))
        val response = MoviesResponse(page = 1, results = movies)

        val movieService: MovieService = mock {
            onBlocking { getNowPlayingMovies(anyString()) } doReturn
                    response
        }*/


        runTest {
            val service = MovieNetworkProvider.getMovieApiService()
            val movie = service.getMovieDetails(1064486)
            //val movieLiveData = movies2
            println(movie.toString())

        }
    }
}