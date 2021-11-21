package com.zerir.networking.domain

import com.zerir.networking.domain.model.PlanetsResponse
import com.zerir.networking.network.Resource

interface Repository {

    suspend fun getAllPlanets(): Resource<PlanetsResponse>

}