package es.rafapuig.movieapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


@Entity(tableName = "movies_genres",
    primaryKeys = ["movie_id", "genre_id"], foreignKeys = [
        ForeignKey(
            parentColumns = ["id"], childColumns = ["movie_id"],
            entity = MovieEntity::class,
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            deferred = false
        )], indices = [Index("genre_id", orders = [Index.Order.DESC])])
class MovieGenreCrossRef (
    @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "genre_id") val genreId: Int
)
