package com.example.fina.utils.ext

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.fina.R
import com.github.mikephil.charting.charts.LineChart

fun View.setBackgroundBasedOnValue(value: Double) {
    val colorRes = if (value > 0) R.color.increase_color else R.color.light_red
    val color = ContextCompat.getColor(context, colorRes)
    this.setBackgroundColor(color)
}

fun View.updateBackgroundByOnClick(
    selectedId: Int,
    selectedIdView: Int,
) {
    val selectedBackground = R.drawable.custom_menu_selected
    val notSelectedBackground = R.drawable.custom_menu_not_selected
    this.setBackgroundResource(
        if (selectedId == selectedIdView) selectedBackground else notSelectedBackground,
    )
}

fun View.updateBackgroundTimeByOnClick(
    selectedId: Int,
    selectedIdView: Int,
) {
    val selectedBackground = R.drawable.custom_menu_selected
    val notSelectedBackground = R.drawable.custom_time_not_selected
    this.setBackgroundResource(
        if (selectedId == selectedIdView) selectedBackground else notSelectedBackground,
    )
}

fun TextView.updateTextColor(
    selectedId: Int,
    selectedIdView: Int,
) {
    val selectedTextColor = ContextCompat.getColor(context, R.color.white)
    val notSelectedTextColor = ContextCompat.getColor(context, R.color.button_text_Color)
    this.setTextColor(
        if (selectedId == selectedIdView) selectedTextColor else notSelectedTextColor,
    )
}

fun ProgressBar.showWithDelay(
    lineChart: LineChart,
    delayMillis: Long = 700L,
    action: () -> Unit,
) {
    lineChart.clear()
    lineChart.setNoDataText("")
    this.visibility = View.VISIBLE

    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
        lineChart.visibility = View.VISIBLE
        action()
        this@showWithDelay.visibility = View.GONE
    }, delayMillis)
}
