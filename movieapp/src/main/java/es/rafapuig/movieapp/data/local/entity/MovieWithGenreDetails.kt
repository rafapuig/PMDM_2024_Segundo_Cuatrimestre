package es.rafapuig.movieapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieWithGenreDetails(
    @Embedded
    val movie: MovieEntity,
    @Relation(
        parentColumn = "id",
        entity = GenreEntity::class,
        entityColumn = "genre_id",
        associateBy = Junction(
            value = MovieGenreCrossRef::class,
            parentColumn = "movie_id",
            entityColumn = "genre_id")
    )
    val genres: List<GenreEntity>
)
