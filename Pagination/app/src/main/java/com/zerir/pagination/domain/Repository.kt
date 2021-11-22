package com.zerir.pagination.domain

import androidx.paging.PagingData
import com.zerir.pagination.domain.model.Planet
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getAllPlanets(): Flow<PagingData<Planet>>

}