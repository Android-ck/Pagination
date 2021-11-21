package com.zerir.networking.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.zerir.networking.R
import com.zerir.networking.data.RepositoryImpl
import com.zerir.networking.databinding.ActivityMainBinding
import com.zerir.networking.network.NetworkConnection
import com.zerir.networking.network.Resource
import com.zerir.networking.network.retrofit.PlanetsApi
import com.zerir.networking.network.retrofit.RemoteDataSource
import com.zerir.networking.utils.LoadingDialog
import com.zerir.networking.utils.Notify

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        val planetsApi = RemoteDataSource().buildApi(PlanetsApi::class.java)
        MainViewModel.Factory(RepositoryImpl(planetsApi), planetAdapter, NetworkConnection())
    }

    private val planetAdapter by lazy { PlanetAdapter() }

    private val loadingDialog = LoadingDialog()
    private val notify = Notify()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        viewModel.resource.observe(this) { resource ->
            resource?.let {

                planetAdapter.submitList(resource.data?.results)
                showLoader(resource is Resource.Loading)
                handleError(resource.throwable)

                viewModel.clearResource()
            }
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