package es.rafapuig.movieapp

import android.app.Application
import es.rafapuig.movieapp.movies.data.MovieRepositoryPagedImpl
import es.rafapuig.movieapp.trending.tvshows.data.TVShowRepositoryImpl
import es.rafapuig.movieapp.data.local.provider.MovieDatabaseProvider
import es.rafapuig.movieapp.core.data.network.provider.TMDBNetworkProvider
import es.rafapuig.movieapp.movies.data.MovieRepositoryImpl
import es.rafapuig.movieapp.movies.domain.MovieRepository
import es.rafapuig.movieapp.movies.data.network.provider.MovieServiceProvider
import es.rafapuig.movieapp.trending.tvshows.domain.TvShowRepository
import es.rafapuig.movieapp.trending.tvshows.data.network.TvShowsServiceProvider

class TheMovieDBApplication : Application() {

    lateinit var moviesRepository: MovieRepository
    lateinit var movieRepository: MovieRepository

    // Repositorio para TV Shows
    lateinit var tvShowRepository: TvShowRepository

    override fun onCreate() {
        super.onCreate()

        val retrofit = TMDBNetworkProvider.provideRetrofit()

        val movieApiService = MovieServiceProvider.provideService(retrofit)
        val tvShowsApiService = TvShowsServiceProvider.provideService(retrofit)


        val db = MovieDatabaseProvider.provideDatabase(applicationContext)

        //movieRepository = MovieRepositoryImpl(api, db.movieDao)
        movieRepository = MovieRepositoryPagedImpl(movieApiService, db)
        moviesRepository = MovieRepositoryImpl(movieApiService,db.movieDao)

        tvShowRepository = TVShowRepositoryImpl(tvShowsApiService)
    }
}