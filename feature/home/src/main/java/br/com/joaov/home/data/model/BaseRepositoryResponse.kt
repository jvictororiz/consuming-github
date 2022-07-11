package br.com.joaov.home.data.model

import com.google.gson.annotations.SerializedName

internal data class BaseRepositoryResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean? = false,
    @SerializedName("total_count")
    val totalCount: Int? = 0,
    val items: List<RepositoryResponse>
)