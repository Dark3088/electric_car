package com.dark.lord.electriccar.ui

import android.os.Bundle
import androidx.core.text.HtmlCompat.fromHtml
import android.text.Spanned
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY

import com.dark.lord.electriccar.R
import com.dark.lord.electriccar.databinding.ActivityCalculateBinding
import com.dark.lord.electriccar.extensions.getFloatValueFromText

class CalculateAutonomy : AppCompatActivity() {

    private val binding by lazy { ActivityCalculateBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.ibCloseButton.setOnClickListener { finish() }
        binding.btCalculateResult.setOnClickListener {
            calculate()
        }
    }

    private fun calculate() {
        val priceValue = binding.etPrice.getFloatValueFromText()
        val kmValue = binding.etKmRun.getFloatValueFromText()

        val result = priceValue / kmValue

        val text: String = getString(R.string.placeholder_result, result)
        val styledText: Spanned = fromHtml(text, FROM_HTML_MODE_LEGACY)

        if (result.isNaN().not())
            binding.tvResultText.text = styledText

    }
}