package com.example.fina.screen.detail

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.fina.R
import com.example.fina.data.model.PriceRecord
import com.example.fina.databinding.FragmentDetailBinding
import com.example.fina.utils.LargeValueFormatter
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun FragmentDetailBinding.setupChartAppearance(context: Context) {
    val xAxis = this.lineChart.xAxis
    xAxis.setDrawGridLines(false)
    xAxis.setDrawAxisLine(false)
    xAxis.setDrawLabels(false)

    val leftAxis = this.lineChart.axisLeft
    leftAxis.setDrawGridLines(true)
    leftAxis.setDrawAxisLine(false)
    leftAxis.gridColor = ContextCompat.getColor(context, R.color.purple)
    leftAxis.valueFormatter = LargeValueFormatter()

    val rightAxis = this.lineChart.axisRight
    rightAxis.setDrawGridLines(true)
    rightAxis.setDrawAxisLine(false)
    rightAxis.setDrawLabels(false)
    rightAxis.gridColor = ContextCompat.getColor(context, R.color.purple)
    this.lineChart.legend.isEnabled = false
    this.lineChart.description.isEnabled = false
}

fun FragmentDetailBinding.createOnChartValueSelectedListener(prices: List<PriceRecord>): OnChartValueSelectedListener {
    return object : OnChartValueSelectedListener {
        override fun onValueSelected(
            e: Entry?,
            h: Highlight?,
        ) {
            e?.let {
                handleChartValueSelected(it, prices)
            }
        }

        override fun onNothingSelected() {
            infoBox.visibility = View.GONE
        }
    }
}

fun FragmentDetailBinding.handleChartValueSelected(
    e: Entry,
    prices: List<PriceRecord>,
) {
    val index = e.x.toInt()
    val reversedPrices = prices.reversed()

    if (index in reversedPrices.indices) {
        val selectedPriceRecord = reversedPrices[index]
        updatePriceAndDateViews(selectedPriceRecord.timestamp, e.y)
        updateInfoBoxPosition(e)
    }
}

private fun FragmentDetailBinding.updatePriceAndDateViews(
    timestamp: Long,
    price: Float,
) {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    val formattedDate = sdf.format(Date(timestamp * 1000)) // Convert from seconds to milliseconds

    priceTextView.text = price.toString()
    dateTextView.text = formattedDate
}

private fun FragmentDetailBinding.updateInfoBoxPosition(e: Entry) {
    val transformer = lineChart.getTransformer(lineChart.data.getDataSetByIndex(0).axisDependency)
    val pixelPos =
        FloatArray(2).apply {
            this[0] = e.x
            this[1] = e.y
        }
    transformer.pointValuesToPixel(pixelPos)

    val fixedDistanceFromXAxis = 20
    infoBox.apply {
        x = (pixelPos[0] - (width / 2)).coerceIn(0f, (lineChart.width - width).toFloat())
        y = (fixedDistanceFromXAxis - height).toFloat().coerceIn(0f, (lineChart.height - height).toFloat())
        visibility = View.VISIBLE
    }
}
