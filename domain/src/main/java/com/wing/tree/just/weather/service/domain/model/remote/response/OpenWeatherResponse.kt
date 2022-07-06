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
            // val dt_txt: String,
            // val city: City
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

            data class City(
                val id: Int,
                val name: String,
                val coord: Coord,
                val country: String,
                val population: Int,
                val timezone: Int,
                val sunrise: Long,
                val sunset: Long
            ) {
                data class Coord(
                    val lat: Float,
                    val lon: Float
                )
            }
        }
    }

    data class Weather(
        val coord: Coord,
        val weather: List<Weather>,
        val base: String,
        val main: Main,
        val visibility: Int,
        val wind: Wind,
        val clouds: Clouds,
        val dt: Long,
        val sys: Sys,
        val timezone: Int,
        val id: Int,
        val name: String,
        val cod: Int
    ) : OpenWeatherResponse() {
        data class Coord(
            val lon: Float,
            val lat: Float
        )

        data class Weather(
            val id: Int,
            val main: String,
            val description: String,
            val icon: String
        )

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

        data class Wind(
            val speed: Float,
            val deg: Int,
            val gust: Float
        )

        data class Clouds(
            val all: Int
        )

        data class Sys(
            val type: Int,
            val id: Int,
            val message: Float,
            val country: String,
            val sunrise: Long,
            val sunset: Long
        )
    }
}