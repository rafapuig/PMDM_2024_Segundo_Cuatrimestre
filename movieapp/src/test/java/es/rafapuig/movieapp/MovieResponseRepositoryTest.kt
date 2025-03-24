package es.rafapuig.movieapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.rafapuig.movieapp.data.network.provider.TMDBNetworkProvider
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class MovieResponseRepositoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun fetchMovies() {
        /*val movies = listOf(MovieResponse(id = 3), MovieResponse(id = 4))
        val response = MoviesResponse(page = 1, TVShowListInfos = movies)

        val movieService: MovieService = mock {
            onBlocking { getNowPlayingMovies(anyString()) } doReturn
                    response
        }*/


        runTest {
            val service = TMDBNetworkProvider.getTheMovieDBApiService()
            val movie = service.getMovieDetails(1064486)
            //val movieLiveData = movies2
            println(movie.toString())

        }
    }
}