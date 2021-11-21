package com.zerir.networking.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zerir.networking.domain.Repository
import com.zerir.networking.domain.model.Planet
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(private val planetPagingResource: PlanetPagingResource) : Repository {

    override fun getAllPlanets(): Flow<PagingData<Planet>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { planetPagingResource }
        ).flow
    }

}