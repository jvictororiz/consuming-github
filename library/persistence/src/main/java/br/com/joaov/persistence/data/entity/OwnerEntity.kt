package br.com.joaov.persistence.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OwnerEntity(
    @PrimaryKey
    val idUser: Int,
    val login: String,
    val avatarUrl: String,
    val urlRepository: String
)
