package com.example.fina.utils

import com.github.mikephil.charting.formatter.ValueFormatter

class LargeValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return when {
            value >= 1_000_000_000 -> String.format("%.1fB", value / 1_000_000_000)
            value >= 1_000_000 -> String.format("%.1fM", value / 1_000_000)
            value >= 1_000 -> String.format("%.1fK", value / 1_000)
            else -> value.toString()
        }
    }
}
