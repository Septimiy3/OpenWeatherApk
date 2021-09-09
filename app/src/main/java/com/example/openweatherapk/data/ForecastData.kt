package com.example.openweatherapk.data

import kotlin.collections.List

class ForecastData {

    private var coord: Coord? = null
    private var main: Main? = null
    private var name: String? = null
    private var day : Day? = null

    private lateinit var list : List<ForecastData>

    fun getDay() : Day?{
        return day
    }
    fun setDay(day : Day){
        this.day = day
    }

    fun getCoord(): Coord? {
        return coord
    }

    fun setCoord(coord: Coord?) {
        this.coord = coord
    }

    fun getMain(): Main? {
        return main
    }

    fun setMain(main: Main?) {
        this.main = main
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }


}