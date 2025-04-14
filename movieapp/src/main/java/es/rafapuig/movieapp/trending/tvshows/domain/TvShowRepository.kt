package es.rafapuig.movieapp.trending.tvshows.domain

import androidx.lifecycle.LiveData
import es.rafapuig.movieapp.trending.tvshows.domain.model.TVShow

interface TvShowRepository {

    suspend fun fetchTrendingTVShows() : List<TVShow>

    //suspend fun fetchTrendingTVShowsAsLiveData() : LiveData<List<TVShow>>

}