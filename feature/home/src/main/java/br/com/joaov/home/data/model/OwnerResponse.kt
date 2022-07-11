package br.com.joaov.home.data.model

import com.google.gson.annotations.SerializedName

internal data class OwnerResponse(
    val id: Int,
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val urlRepository: String
)
