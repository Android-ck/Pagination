package com.zerir.networking.data

import com.zerir.networking.domain.Repository
import com.zerir.networking.domain.model.PlanetsResponse
import com.zerir.networking.network.AsyncCall
import com.zerir.networking.network.Resource
import com.zerir.networking.network.retrofit.PlanetsApi

class RepositoryImpl(private val planetsApi: PlanetsApi) : Repository, AsyncCall {

    override suspend fun getAllPlanets(): Resource<PlanetsResponse> = invokeAsyncCall {
        planetsApi.getPlanets()
    }

}