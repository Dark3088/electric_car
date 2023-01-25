package com.dark.lord.electriccar.extensions

import android.widget.EditText

// For retrieving float values out of text
fun EditText.getFloatValueFromText(): Float =
    run {
        if (text.isNotEmpty()) text.toString().toFloat()
        else 0.toFloat()
    }