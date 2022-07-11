package br.com.joaov.persistence.data

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.joaov.persistence.data.dao.RepositoryDao
import br.com.joaov.persistence.data.entity.OwnerEntity
import br.com.joaov.persistence.data.entity.RepositoryEntity


@Database(entities = [RepositoryEntity::class, OwnerEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun repositoryDao(): RepositoryDao
}