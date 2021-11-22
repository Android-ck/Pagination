package com.zerir.pagination.domain

import androidx.paging.PagingData
import com.zerir.pagination.domain.model.Passenger
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getAllPassengers(): Flow<PagingData<Passenger>>

}