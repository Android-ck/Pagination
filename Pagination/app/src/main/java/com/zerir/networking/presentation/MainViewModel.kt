package com.zerir.networking.presentation

import androidx.lifecycle.*
import com.zerir.networking.domain.Repository
import com.zerir.networking.domain.model.PlanetsResponse
import com.zerir.networking.network.NetworkConnection
import com.zerir.networking.network.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository,
    val planetAdapter: PlanetAdapter,
    val networkConnection: NetworkConnection,
) : ViewModel() {

    private var planetsJob: Job? = null

    private val _resource = MutableLiveData<Resource<PlanetsResponse>?>()
    val resource: LiveData<Resource<PlanetsResponse>?> get() = _resource

    init {
        loadPlanet()
    }

    private fun loadPlanet() {
        planetsJob?.cancel()
        planetsJob = viewModelScope.launch {
            _resource.value = Resource.Loading()
            //delay to see loading indicator
            delay(2000)
            _resource.value = repository.getAllPlanets()
        }
    }

    fun clearResource() { _resource.value = null }

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