package com.dark.lord.electriccar.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dark.lord.electriccar.databinding.ActivityCalculateBinding
import com.dark.lord.electriccar.getFloatValueFromText

class CalculateAutonomy : AppCompatActivity() {

    private val binding by lazy { ActivityCalculateBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.ibCloseButton.setOnClickListener{ finish() }
        binding.btCalculateResult.setOnClickListener {
            calculate()
        }
    }

    private fun calculate(){
        val priceValue = binding.etPrice.getFloatValueFromText()
        val kmValue = binding.etKmRun.getFloatValueFromText()

        val result = priceValue / kmValue
        binding.tvResultText.text = result.toString()
    }
}