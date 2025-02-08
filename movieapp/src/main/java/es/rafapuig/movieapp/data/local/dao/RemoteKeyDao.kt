package es.rafapuig.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.rafapuig.movieapp.data.local.entity.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Query("SELECT * FROM remote_keys")
    suspend fun remoteKey():RemoteKey

    @Query("DELETE FROM remote_keys")
    suspend fun delete()
}