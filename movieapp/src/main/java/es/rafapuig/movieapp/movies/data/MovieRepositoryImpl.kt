package es.rafapuig.movieapp.movies.data

import androidx.paging.PagingData
import es.rafapuig.movieapp.data.local.dao.MovieDao
import es.rafapuig.movieapp.movies.data.mappers.toDatabase
import es.rafapuig.movieapp.movies.domain.MovieRepository
import es.rafapuig.movieapp.movies.domain.model.Movie
import es.rafapuig.movieapp.movies.data.mappers.toDomain
import es.rafapuig.movieapp.movies.data.network.api.MovieApiService

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepositoryImpl(
    private val apiService: MovieApiService,
    private val movieDao: MovieDao
) : MovieRepository {

    // Devuelve la lista de peliculas recuperada directamente del API
    // Transformadas a modelo del dominio
    override suspend fun fetchMovies(): List<Movie> {
        val response = apiService.getNowPlayingMovies()
        return response.results.map { movieResponse ->
            movieResponse.toDomain()
        }
    }

    override fun fetchMoviesFlow(): Flow<List<Movie>> {
        return flow {
            // Emitimos las peliculas que tenemos en cache primero
            val cachedMovies = movieDao.getNowPlayingMovies().map { it.toDomain() }
            emit(cachedMovies)

            var page: Int = 1
            var totalPages: Int = 0

            //Ahora recuperamos las películas del API página a página
            do {
                val moviesResponse = apiService.getNowPlayingMovies(page = page)
                totalPages = moviesResponse.totalPages
                // Las insertamos en la BD cache
                movieDao.insertAll(moviesResponse.results.map { it.toDatabase() })
            } while (page++ < totalPages)


            val updatedMovies = movieDao.getNowPlayingMovies().map { it.toDomain() }
            //emit(movieService.getMovies().TVShowListInfos.map { it.toDomain() })
            // Y volvemos a emitir
            emit(updatedMovies)
        }.flowOn(Dispatchers.IO)
    }

    override fun fetchMoviesPagingFlow(): Flow<PagingData<Movie>> {
        TODO("Not yet implemented")
    }
}