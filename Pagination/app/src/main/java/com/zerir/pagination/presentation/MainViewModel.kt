package com.zerir.pagination.presentation

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.zerir.pagination.domain.Repository
import com.zerir.pagination.network.NetworkConnection

class MainViewModel(
    private val repository: Repository,
    val passengerAdapter: PassengerAdapter,
    val networkConnection: NetworkConnection,
) : ViewModel() {

    val planets get() = repository.getAllPassengers().cachedIn(viewModelScope)

    class Factory(
        private val repository: Repository,
        private val passengerAdapter: PassengerAdapter,
        private val networkConnection: NetworkConnection,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository, passengerAdapter, networkConnection) as T
        }

    }

}