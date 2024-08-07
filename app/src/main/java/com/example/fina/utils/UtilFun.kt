package com.example.fina.utils

import kotlin.math.absoluteValue

fun CharSequence?.toTimePeriod(): TimePeriod? {
    return when (this) {
        "1h" -> TimePeriod.ONE_HOUR
        "24h" -> TimePeriod.TWENTY_FOUR_HOURS
        "7d" -> TimePeriod.SEVEN_DAYS
        "1m" -> TimePeriod.THIRTY_DAYS
        "3m" -> TimePeriod.THREE_MONTHS
        "1y" -> TimePeriod.ONE_YEAR
        "3y" -> TimePeriod.THREE_YEARS
        "5y" -> TimePeriod.FIVE_YEARS
        else -> null
    }
}

fun CharSequence?.toMarketCapInterval(): MarketCapInterval? {
    return when (this) {
        "1h" -> MarketCapInterval.HOUR
        "24h" -> MarketCapInterval.DAY
        "7d" -> MarketCapInterval.WEEK
        "1m" -> MarketCapInterval.MONTH
        else -> null
    }
}

fun formatChangeValue(change: String): String {
    val numericValue = change.toDoubleOrNull() ?: return change
    return if (numericValue >= 0) {
        change
    } else {
        numericValue.absoluteValue.toString()
    }
}

fun changeImageToPng(input: String): String {
    val prefix = input.substring(0, input.length - 4)
    return "$prefix.png"
}

fun Float.toLargeValueFormat(): String {
    return when {
        this >= 1_000_000_000 -> String.format("%.1fB", this / 1_000_000_000)
        this >= 1_000_000 -> String.format("%.1fM", this / 1_000_000)
        this >= 1_000 -> String.format("%.1fK", this / 1_000)
        else -> this.toString()
    }
}
