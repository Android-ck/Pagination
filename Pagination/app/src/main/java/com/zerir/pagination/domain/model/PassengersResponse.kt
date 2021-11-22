package com.zerir.pagination.domain.model

data class PassengersResponse(
    val totalPassengers: Long = 0,
    val totalPages: Long = 0,
    val data: List<Passenger> = listOf(),
)
