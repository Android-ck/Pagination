package com.zerir.networking.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.zerir.networking.R
import com.zerir.networking.data.PlanetPagingResource
import com.zerir.networking.data.RepositoryImpl
import com.zerir.networking.databinding.ActivityMainBinding
import com.zerir.networking.network.NetworkConnection
import com.zerir.networking.network.retrofit.PlanetsApi
import com.zerir.networking.network.retrofit.RemoteDataSource
import com.zerir.networking.utils.LoadingDialog
import com.zerir.networking.utils.Notify
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        val planetsApi = RemoteDataSource().buildApi(PlanetsApi::class.java)
        val planetPagingResource = PlanetPagingResource(planetsApi)
        MainViewModel.Factory(RepositoryImpl(planetPagingResource), planetAdapter, NetworkConnection())
    }

    private val planetAdapter by lazy { PlanetAdapter() }

    private val loadingDialog = LoadingDialog()
    private val notify = Notify()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        lifecycleScope.launch {
            viewModel.planets.collectLatest { data ->
                planetAdapter.submitData(data)
            }
        }

        planetAdapter.addLoadStateListener { loadState ->
            showLoader(loadState.refresh is LoadState.Loading)

            val error = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                //loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            handleError(error?.error)
        }
    }

    private fun showLoader(showLoader: Boolean) {
        if (showLoader) {
            loadingDialog.show(supportFragmentManager, "loading")
        } else {
            if(loadingDialog.isAdded) loadingDialog.dismiss()
        }
    }

    private fun handleError(throwable: Throwable?) {
        if (throwable == null) return
        val cause = if(throwable.message.isNullOrBlank()) "" else "\n-> ${throwable.message}"
        val message = "${getString(R.string.something_went_wrong)}$cause"
        notify.showSnackBar(
            message = message,
            view = binding.root,
            time = Snackbar.LENGTH_INDEFINITE,
            actionName = getString(R.string.dismiss),
        ) {}
    }

    private fun getConnectivityManager() = NetworkConnection.getConnectivityManager(this)

    override fun onResume() {
        super.onResume()
        NetworkConnection.registerCallbacks(
            getConnectivityManager(),
            viewModel.networkConnection.getNetworkRequest(),
            viewModel.networkConnection.getNetworkCallBack(),
        )
    }

    override fun onPause() {
        super.onPause()
        NetworkConnection.unRegisterCallbacks(
            getConnectivityManager(),
            viewModel.networkConnection.getNetworkCallBack(),
        )
    }

}