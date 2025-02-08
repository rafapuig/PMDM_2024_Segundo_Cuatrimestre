package es.rafapuig.movieapp

import android.app.Application
import es.rafapuig.movieapp.data.MovieRepositoryImpl
import es.rafapuig.movieapp.data.MovieRepositoryPagedImpl
import es.rafapuig.movieapp.data.local.provider.MovieDatabaseProvider
import es.rafapuig.movieapp.data.network.provider.MovieNetworkProvider
import es.rafapuig.movieapp.domain.MovieRepository

class MovieApplication : Application() {

    lateinit var movieRepository: MovieRepository

    override fun onCreate() {
        super.onCreate()

        val db = MovieDatabaseProvider.getDatabase(applicationContext)
        val api = MovieNetworkProvider.getMovieApiService()

        //movieRepository = MovieRepositoryImpl(api, db.movieDao)
        movieRepository = MovieRepositoryPagedImpl(api, db)
    }
}