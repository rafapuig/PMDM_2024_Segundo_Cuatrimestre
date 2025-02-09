package es.rafapuig.movieapp.data.mappers

import es.rafapuig.movieapp.data.local.entity.GenreEntity
import es.rafapuig.movieapp.data.network.model.GenreResponse

fun GenreResponse.toDatabase() = GenreEntity(
    id = id,
    name = name
)
