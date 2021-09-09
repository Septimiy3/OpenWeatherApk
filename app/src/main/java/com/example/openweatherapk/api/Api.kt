package com.example.openweatherapk.api

import com.example.openweatherapk.data.ForecastData
import com.example.openweatherapk.data.WeatherData
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

import retrofit2.http.Query



interface Api {


    @GET("data/2.5/weather")
    fun getWeatherDataByCity(
        @Query("q") city: String?,
        @Query("appid") appId: String?,
        @Query("units") units: String?
    ): Observable<WeatherData?>?


    @GET("data/2.5/weather")
    fun getWeatherDataByCoordinate(
        @Query("lat") lat : Double?,
        @Query("lon") lon : Double?,
        @Query("appid") appId: String?,
        @Query("units") units: String?
    ) : Observable<WeatherData?>?

    @GET("data/2.5/forecast")
    fun getForecastData(
        @Query("q") city:String?,
        @Query("appid") appId: String?,
        @Query("units") units: String?
    ): Observable<ForecastData>?




    object Instance {

        private val retrofit: Retrofit

            private get() {
                val okHttpClientBuilder = OkHttpClient.Builder()
                val retrofitBuilder = Retrofit.Builder()
                retrofitBuilder.baseUrl(DOMAIN)
                retrofitBuilder.addConverterFactory(GsonConverterFactory.create())
                retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                retrofitBuilder.client(okHttpClientBuilder.build())
                return retrofitBuilder.build()
            }

        fun getApi(): Api? {
            return retrofit.create(Api::class.java)
        }
    }

    companion object {
        const val DOMAIN = "https://api.openweathermap.org/"
    }
}