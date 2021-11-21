package com.zerir.networking.domain

import androidx.paging.PagingData
import com.zerir.networking.domain.model.Planet
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getAllPlanets(): Flow<PagingData<Planet>>

}