package es.rafapuig.movieapp.trending.tvshows.domain

import es.rafapuig.movieapp.trending.tvshows.domain.model.TVShow

interface TvShowRepository {

    suspend fun fetchTrendingTVShows() : List<TVShow>

}