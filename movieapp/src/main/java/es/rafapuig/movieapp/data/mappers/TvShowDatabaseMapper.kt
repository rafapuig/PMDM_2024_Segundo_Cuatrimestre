package es.rafapuig.movieapp.data.mappers

import es.rafapuig.movieapp.data.local.entity.TvShowEntity
import es.rafapuig.movieapp.trending.tvshows.data.model.TVShowListInfo

fun TVShowListInfo.toDatabase() = TvShowEntity(
    id = id,
    name = name,
    overview = overview,
    posterPath = posterPath
)