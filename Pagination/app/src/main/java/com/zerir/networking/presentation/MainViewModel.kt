package com.zerir.networking.presentation

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.zerir.networking.domain.Repository
import com.zerir.networking.network.NetworkConnection

class MainViewModel(
    private val repository: Repository,
    val planetAdapter: PlanetAdapter,
    val networkConnection: NetworkConnection,
) : ViewModel() {

    val planets get() = repository.getAllPlanets().cachedIn(viewModelScope)

    class Factory(
        private val repository: Repository,
        private val planetAdapter: PlanetAdapter,
        private val networkConnection: NetworkConnection,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository, planetAdapter, networkConnection) as T
        }

    }

}