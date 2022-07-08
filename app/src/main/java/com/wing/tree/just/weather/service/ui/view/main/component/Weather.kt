package com.wing.tree.just.weather.service.ui.view.main.component

import android.location.Address
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.wing.tree.just.weather.service.constant.DEGREE_SIGN
import com.wing.tree.just.weather.service.constant.icons
import com.wing.tree.just.weather.service.domain.constant.EMPTY
import com.wing.tree.just.weather.service.domain.model.local.openweather.Weather
import com.wing.tree.just.weather.service.ui.view.main.WeatherState
import kotlin.math.roundToInt

@Composable
fun Weather(
    modifier: Modifier,
    address: Address?,
    uiState: WeatherState
) {
    when(uiState) {
        is WeatherState.Loading -> Unit
        is WeatherState.Content -> uiState.weather?.let { weather ->
            WeatherContent(
                modifier = modifier,
                address = address,
                weather = weather
            )
        }
        is WeatherState.Error -> Unit
    }
}

@Composable
fun WeatherContent(
    modifier: Modifier,
    address: Address?,
    weather: Weather
) {
    val tempText = "${weather.temp.roundToInt()}$DEGREE_SIGN"
    val locality = address?.locality
    val subLocality = address?.subLocality

    Row(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(0.5F),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = tempText)
            Text(text = locality ?: subLocality ?: EMPTY)
        }

        Column(
            modifier = Modifier.fillMaxWidth(0.5F),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val painter = icons[weather.icon]?.let {
                painterResource(id = it)
            }

            painter?.let { Image(painter = it, contentDescription = null) }
        }
    }
}