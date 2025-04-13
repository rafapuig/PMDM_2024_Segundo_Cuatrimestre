package es.rafapuig.movieapp.trending.tvshows.data.network

import es.rafapuig.movieapp.trending.tvshows.data.api.TvShowsApiService
import retrofit2.Retrofit

object TvShowsServiceProvider {

    fun provideService(retrofit: Retrofit) =
        retrofit.create(TvShowsApiService::class.java)

}