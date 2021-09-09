package com.example.openweatherapk

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.core.app.ActivityCompat
import com.example.openweatherapk.api.Api
import com.example.openweatherapk.data.ForecastData
import com.example.openweatherapk.data.WeatherData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.internal.util.NotificationLite.accept
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


import android.R.attr.data




class MainActivity : AppCompatActivity() {

    private val mApi: Api? = Api.Instance.getApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_current_weather.setOnClickListener { inat() }
        btn_current_location.setOnClickListener { getLocationCurrent() }
        btn_current_coordinate.setOnClickListener { init() }

        init()

    }

    fun inat(){
        receiveWeather(city = et_change_city.text.toString())
    }
    fun init() {
        mApi!!.getForecastData("Moscow","0707fc33b05c862d2ba87d8e6a905f86","metric")
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe { forecastData -> showForecastData(forecastData!!) }
    }
    private fun showForecastData(forecastData: ForecastData){


//        val sAdapter = SimpleAdapter(
//            this, data, R.layout.item_weather,
//            from, to)
    }

    private fun receiveWeather(city: String) {
        mApi!!.getWeatherDataByCity(city, "0707fc33b05c862d2ba87d8e6a905f86", "metric")
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe { weatherData -> showWeatherMarker(weatherData!!) }
    }

    private fun showWeatherMarker(weatherData: WeatherData) {
        txt_city.text = et_change_city.text.toString()
        txt_current_weather.text = weatherData.getMain()?.temp.toString()
    }

    private fun showWeatherCoordinate(weatherData: WeatherData){
        txt_city_weather_location.text = weatherData.getMain()?.temp.toString()
        txt_city_name_coordinate.text = weatherData.getName().toString()
        current_day.text = weatherData.getDay().toString()

    }

    fun getLocationCurrent(){
        val locationResult = object : MyLocation.LocationResult() {

            override fun gotLocation(location: Location?) {

                val lat = location!!.latitude
                val lon = location.longitude

//                Toast.makeText(this@MainActivity, "$lat --SLocRes-- $lon", Toast.LENGTH_SHORT).show()
                mApi!!.getWeatherDataByCoordinate(lat,lon, "0707fc33b05c862d2ba87d8e6a905f86", "metric")
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe { weatherData -> showWeatherCoordinate(weatherData!!) }

            }
        }

        val myLocation = MyLocation()
        myLocation.getLocation(this, locationResult)
    }

}