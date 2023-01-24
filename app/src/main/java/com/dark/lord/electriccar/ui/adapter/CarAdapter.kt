package com.dark.lord.electriccar.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dark.lord.electriccar.R
import com.dark.lord.electriccar.domain.Car

class CarAdapter(private val cars: List<Car>, private val isFavorite: Boolean = false) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    var carItemListener: (Car) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.priceValue.text = cars[position].preco
        holder.batteryValue.text = cars[position].bateria
        holder.horsePower.text = cars[position].potencia
        holder.recharge.text = cars[position].recarga
        if (isFavorite) holder.favoriteStar.setImageResource(R.drawable.ic_filled_star)

        holder.favoriteStar.setOnClickListener {
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
            holder.favoriteStar.setImageResource(R.drawable.ic_filled_star)
        } else {
            holder.favoriteStar.setImageResource(R.drawable.ic_star)
        }
    }

    override fun getItemCount(): Int = cars.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val priceValue: TextView
        val batteryValue: TextView
        val horsePower: TextView
        val recharge: TextView
        val favoriteStar: ImageButton

        init {
            view.apply {
                priceValue = findViewById(R.id.tv_price_value)
                batteryValue = findViewById(R.id.tv_battery_value)
                horsePower = findViewById(R.id.tv_horse_pow_value)
                recharge = findViewById(R.id.tv_recharge_value)
                favoriteStar = findViewById(R.id.ib_favorite)
            }
        }
    }
}