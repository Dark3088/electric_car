package com.dark.lord.electriccar.data


import com.dark.lord.electriccar.domain.Car
import retrofit2.Call
import retrofit2.http.GET

interface CarsApi  {

    @GET("cars.json")
    fun getAllCars() : Call<List<Car>>
}