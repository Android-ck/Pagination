package com.zerir.networking.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zerir.networking.domain.model.Planet
import com.zerir.networking.network.retrofit.PlanetsApi

class PlanetPagingResource(private val planetsApi: PlanetsApi) :
    PagingSource<Int, Planet>() {

    override fun getRefreshKey(state: PagingState<Int, Planet>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Planet> {
        return try {
            val page = params.key ?: FIRST_PAGE_INDEX
            val response = planetsApi.getPlanets(page)
            val prevPag = if (page == FIRST_PAGE_INDEX) null else page - 1
            var nextPage: Int? = null
            if(response.next != null) {
                nextPage = page + 1
            }
            LoadResult.Page(
                response.results,
                prevKey = prevPag,
                nextKey = nextPage
            )
        } catch (throwable: Throwable) {
            LoadResult.Error(throwable)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}