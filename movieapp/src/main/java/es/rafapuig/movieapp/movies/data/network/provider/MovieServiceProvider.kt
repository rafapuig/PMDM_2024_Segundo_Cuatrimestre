package es.rafapuig.movieapp.movies.data.network.provider

import es.rafapuig.movieapp.movies.data.network.api.MovieApiService
import retrofit2.Retrofit

object MovieServiceProvider {

    fun provideService(retrofit: Retrofit) =
        retrofit.create(MovieApiService::class.java)

}