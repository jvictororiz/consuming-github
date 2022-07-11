package br.com.joaov.home.data.model

import com.google.gson.annotations.SerializedName

internal data class RepositoryResponse(
    val id: Int,
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("html_url")
    val url: String,
    val owner: OwnerResponse
)
