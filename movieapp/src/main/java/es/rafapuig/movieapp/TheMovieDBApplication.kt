package es.rafapuig.movieapp

import android.app.Application
import es.rafapuig.movieapp.data.MovieRepositoryPagedImpl
import es.rafapuig.movieapp.data.TVShowRepositoryImpl
import es.rafapuig.movieapp.data.local.provider.MovieDatabaseProvider
import es.rafapuig.movieapp.data.network.provider.TMDBNetworkProvider
import es.rafapuig.movieapp.domain.MockTvShowRepository
import es.rafapuig.movieapp.domain.MovieRepository
import es.rafapuig.movieapp.domain.TvShowRepository

class TheMovieDBApplication : Application() {

    lateinit var movieRepository: MovieRepository

    // Repositorio para TV Shows
    lateinit var tvShowRepository: TvShowRepository

    override fun onCreate() {
        super.onCreate()

        val apiService = TMDBNetworkProvider.getTheMovieDBApiService()

        val db = MovieDatabaseProvider.provideDatabase(applicationContext)

        //movieRepository = MovieRepositoryImpl(api, db.movieDao)
        movieRepository = MovieRepositoryPagedImpl(apiService, db)


        tvShowRepository = TVShowRepositoryImpl(apiService)
    }
}