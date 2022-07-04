package com.wing.tree.just.weather.service.domain.model.remote.response

sealed class OpenWeatherResponse {
    @Suppress("unused")
    data class Forecast (
        val cod: String,
        val message: Int,
        val cnt: Int,
        val list: List<Item>
    ): OpenWeatherResponse() {
        data class Item(
            val dt: Long,
            val main: Main,
            val weather: List<Weather>,
            // val clouds: Clouds,
            // val wind: Wind,
            // val visibility: Int,
            // val pop: Float,
            // val sys: Sys,
            // val dt_txt: String
        ) {
            data class Main(
                val temp: Float,
                val feels_like: Float,
                val temp_min: Float,
                val temp_max: Float,
                val pressure: Int,
                val sea_level: Int,
                val grnd_level: Int,
                val humidity: Int,
                val temp_kf: Float,
            )

            data class Weather(
                val id: Int,
                val main: String,
                val description: String,
                val icon: String
            )

            data class Clouds(
                val all: Int
            )

            data class Wind(
                val speed: Float,
                val deg: Int,
                val gust: Float
            )

            data class Sys(
                val pod: String
            )
        }
    }

    object Weather : OpenWeatherResponse()
}