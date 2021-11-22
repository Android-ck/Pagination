package com.zerir.pagination.network.retrofit

import com.zerir.pagination.domain.model.PassengersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PassengersApi {

    @GET("passenger/")
    suspend fun getPassengers(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): PassengersResponse

}