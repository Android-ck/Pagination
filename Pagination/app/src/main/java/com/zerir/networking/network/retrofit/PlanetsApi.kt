package com.zerir.networking.network.retrofit

import com.zerir.networking.domain.model.PlanetsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlanetsApi {

    @GET("planets/")
    suspend fun getPlanets(@Query("page") page: Int = 1): PlanetsResponse

}