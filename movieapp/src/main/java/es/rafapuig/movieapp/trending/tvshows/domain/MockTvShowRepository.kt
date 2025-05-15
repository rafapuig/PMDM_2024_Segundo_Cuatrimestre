package es.rafapuig.movieapp.trending.tvshows.domain

import es.rafapuig.movieapp.trending.tvshows.domain.model.TVShow

class MockTvShowRepository : TvShowRepository {

    override suspend fun fetchTrendingTVShows(): List<TVShow> {
        return listOf(
            TVShow(id = 1, name = "TV Show 1"),
            TVShow(id = 2)
        )
    }
}