package com.dark.lord.electriccar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.dark.lord.electriccar.R
import com.dark.lord.electriccar.data.local.CarRepository
import com.dark.lord.electriccar.databinding.FragmentFavoriteBinding
import com.dark.lord.electriccar.domain.Car
import com.dark.lord.electriccar.ui.adapter.CarAdapter

class FavoriteFragment : Fragment() {

    lateinit var allCarsList : List <Car>

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        setUpFavoriteList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpView(view: View) {
        view.apply {
            recyclerView = binding.rvFavoriteCarList
            if (recyclerView.isVisible) recyclerView.isVisible = false
        }
    }

    private fun setUpFavoriteList() {
        val repository = CarRepository(requireContext())
        allCarsList = repository.getAllCars()

        val adapter = CarAdapter(allCarsList, isFavorite = true)
        recyclerView.isVisible = true
        recyclerView.adapter = adapter

        adapter.carItemListener = { car ->
            CarRepository(requireContext()).delete(car)
        }
    }
}