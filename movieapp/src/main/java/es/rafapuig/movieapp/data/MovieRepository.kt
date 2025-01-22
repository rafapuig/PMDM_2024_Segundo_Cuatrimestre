package es.rafapuig.movieapp.data

import es.rafapuig.movieapp.data.network.api.MovieService
import es.rafapuig.movieapp.data.network.model.Movie

class MovieRepository(private val movieService: MovieService) {


    suspend fun fetchMovies(): List<Movie> {
        val moviesResponse = movieService.getMovies()
        return moviesResponse.results
    }


}