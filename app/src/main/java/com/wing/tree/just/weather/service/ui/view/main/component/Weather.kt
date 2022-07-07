package com.wing.tree.just.weather.service.ui.view.main.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wing.tree.just.weather.service.constant.DEGREE_SIGN
import com.wing.tree.just.weather.service.domain.model.local.openweather.Weather

@Composable
fun Weather(
    modifier: Modifier,
    weather: Weather
) {
    Row(modifier = modifier) {
        Column(modifier = Modifier) {
            IconButton(onClick = {  }) {
                Icon(Icons.Rounded.Menu, null)
            }

            Text(text = "${weather.temp}$DEGREE_SIGN")
        }
    }
}