package com.zerir.pagination.domain.model

data class PlanetsResponse(
    val count: Int = 0,
    val next: String?,
    val previous: String?,
    val results: List<Planet> = listOf(),
)
