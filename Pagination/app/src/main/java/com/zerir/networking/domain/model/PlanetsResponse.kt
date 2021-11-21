package com.zerir.networking.domain.model

import com.zerir.networking.domain.model.Planet

data class PlanetsResponse(
    val count: Int = 0,
    val next: String?,
    val previous: String?,
    val results: List<Planet> = listOf(),
)
