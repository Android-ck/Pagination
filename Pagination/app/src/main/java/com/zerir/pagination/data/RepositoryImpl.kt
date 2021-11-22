package com.zerir.pagination.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zerir.pagination.domain.Repository
import com.zerir.pagination.domain.model.Passenger
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(private val passengerPagingResource: PassengerPagingResource) : Repository {

    override fun getAllPassengers(): Flow<PagingData<Passenger>> {
        return Pager(
            config = PagingConfig(
                pageSize = PassengerPagingResource.SIZE_OF_PAGE,
                maxSize = PassengerPagingResource.MAX_LENGTH_OF_ITEMS,
                initialLoadSize = PassengerPagingResource.SIZE_OF_PAGE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { passengerPagingResource }
        ).flow
    }

}