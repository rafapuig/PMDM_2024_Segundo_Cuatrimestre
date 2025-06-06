package es.rafapuig.movieapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey val paginatedServiceId: Int,
    val nextKey: Int?)