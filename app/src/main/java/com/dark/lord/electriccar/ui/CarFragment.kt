package com.dark.lord.electriccar.ui

import android.content.Context

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.dark.lord.electriccar.data.CarsApi
import com.dark.lord.electriccar.data.local.CarRepository
import com.dark.lord.electriccar.databinding.FragmentCarBinding
import com.dark.lord.electriccar.domain.Car
import com.dark.lord.electriccar.ui.adapter.CarAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarFragment : Fragment() {

    private lateinit var carsApi: CarsApi

    private var _binding: FragmentCarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRetrofit()
        setUpView(view)
    }

    override fun onResume() {
        super.onResume()
        if (checkInternetConnection(requireContext())) {
            getAllCars()
        } else {
            showNoInternetInfo()
        }
    }

    private fun setUpRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://igorbag.github.io/cars-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        carsApi = retrofit.create(CarsApi::class.java)
    }

    private fun getAllCars() {
        carsApi.getAllCars().enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if (response.isSuccessful) {
                    binding.pbLoader.isVisible = false

                    // Alert user that there's no internet signal at the moment
                    binding.tvNoInternet.isVisible = false
                    binding.ivInternetOff.isVisible = false

                    response.body()?.let {
                        setUpList(it)
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Some error occurred", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Some unknown error occurred", Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun showNoInternetInfo() {
        binding.tvNoInternet.isVisible = true
        binding.ivInternetOff.isVisible = true
        binding.pbLoader.isVisible = false
        binding.rvCarList.isVisible = false
    }

    private fun setUpList(allCars: List<Car>) {
        val adapter = CarAdapter(allCars)
        binding.rvCarList.isVisible = true
        binding.rvCarList.adapter = adapter

        adapter.carItemListener = { car ->
            CarRepository(requireContext()).saveIfUnavailable(car)
        }
    }

    private fun setUpView(view: View) {
        view.apply {
            if (binding.rvCarList.isVisible) {
                binding.rvCarList.isVisible = false
            }
        }
    }

    private fun checkInternetConnection(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val netWork = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(netWork) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }

        } else {
            val netWorkInfo = connectivityManager.activeNetworkInfo ?: return true
            @Suppress("DEPRECATION")
            return netWorkInfo.isConnected
        }
    }
}