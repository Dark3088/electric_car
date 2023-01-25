package com.dark.lord.electriccar.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dark.lord.electriccar.R
import com.dark.lord.electriccar.databinding.CarItemBinding
import com.dark.lord.electriccar.domain.Car

class CarAdapter(private val cars: List<Car>, private val isFavorite: Boolean = false) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    var carItemListener: (Car) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder.binding) {
            tvPriceValue.text = cars[position].preco
            tvBatteryValue.text = cars[position].bateria
            tvHorsePowValue.text = cars[position].potencia
            tvRecharge.text = cars[position].recarga
        }

        if (isFavorite) holder.binding.ibFavorite.setImageResource(R.drawable.ic_filled_star)
        holder.binding.ibFavorite.setOnClickListener {

            val car = cars[position]
            carItemListener(car)

            car.isFavorite = !car.isFavorite
            setFavorite(car, holder)
        }
    }

    private fun setFavorite(
        car: Car,
        holder: ViewHolder
    ) {
        if (car.isFavorite) {
            holder.binding.ibFavorite.setImageResource(R.drawable.ic_filled_star)
        } else {
            holder.binding.ibFavorite.setImageResource(R.drawable.ic_star)
        }
    }

    override fun getItemCount(): Int = cars.size

    inner class ViewHolder(val binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root)
}