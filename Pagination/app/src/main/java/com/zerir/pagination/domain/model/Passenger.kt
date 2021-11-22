package com.zerir.pagination.domain.model

data class Passenger(
    val __v: Int = 0,
    val _id: String = "",
    val airline: List<Airline> = listOf(),
    val name: String = "",
    val trips: Double = 0.0,
) {

    val airlineLogo get() = if(airline.isNullOrEmpty()) "" else airline[0].logo

}