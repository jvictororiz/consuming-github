package br.com.joaov.persistence.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repository")
data class RepositoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val fullName: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val url: String,
    val page: Int,
    @Embedded
    val owner: OwnerEntity
)
