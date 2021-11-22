package com.zerir.pagination.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zerir.pagination.domain.model.Passenger
import com.zerir.pagination.network.retrofit.PassengersApi

class PassengerPagingResource(private val passengersApi: PassengersApi) :
    PagingSource<Int, Passenger>() {

    override fun getRefreshKey(state: PagingState<Int, Passenger>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Passenger> {
        return try {
            val page = params.key ?: FIRST_PAGE_INDEX
            val response = passengersApi.getPassengers(page, SIZE_OF_PAGE)
            val prevPag = if (page == FIRST_PAGE_INDEX) null else page - 1
            val nextPage: Int? = calculateNextPage(page, response.totalPages)
            LoadResult.Page(
                response.data,
                prevKey = prevPag,
                nextKey = nextPage
            )
        } catch (throwable: Throwable) {
            LoadResult.Error(throwable)
        }
    }

    private fun calculateNextPage(currentPage: Int, totalPages: Long): Int? {
        val nextPage = currentPage + 1
        return if(currentPage < totalPages &&
            (nextPage * SIZE_OF_PAGE) < MAX_LENGTH_OF_ITEMS) nextPage
        else null
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 0
        const val SIZE_OF_PAGE = 100
        const val MAX_LENGTH_OF_ITEMS = 1000
    }
}