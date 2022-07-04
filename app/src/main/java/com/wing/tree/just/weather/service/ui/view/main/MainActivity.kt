package com.wing.tree.just.weather.service.ui.view.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.wing.tree.just.weather.service.core.checkPermission
import com.wing.tree.just.weather.service.core.shouldShowRequestPermissionRationale
import com.wing.tree.just.weather.service.ui.theme.JustWeatherServiceTheme
import com.wing.tree.just.weather.service.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    @SuppressLint("MissingPermission")
    private val requestMultiplePermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
        if (map.all { it.value }) {
            viewModel.forecast()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when {
            checkPermission(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION) -> {
                viewModel.forecast()
            }
            shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION) -> {
            }
            else -> {
                requestMultiplePermissionsLauncher.launch(arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION))
            }
        }

        setContent {
            val forecast by viewModel.forecast.observeAsState()

            JustWeatherServiceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    forecast?.let { forecast ->
                        val today = viewModel.today
                        val tomorrow = viewModel.tomorrow

                        val list = forecast.list.filter {
                            it.dateEquals(today) || it.dateEquals(tomorrow)
                        }

                        Forecast(modifier = Modifier.fillMaxWidth(), list = list)
                    }
                }
            }
        }
    }
}