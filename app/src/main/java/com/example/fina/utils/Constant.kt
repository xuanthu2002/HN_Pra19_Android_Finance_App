package com.example.fina.utils

import android.content.Context
import com.example.fina.R
import com.example.fina.utils.base.ItemBase

object Constant {
    const val BASE_URL_COINS = "https://api.coinranking.com/v2/coins?"
    const val BASE_URL_COIN_DETAIL = "https://api.coinranking.com/v2/coin/"
    const val USD_UUID = "yhjMzLPhuIDl"
    const val DEFAULT_LIMIT = 50
    const val API_KEY = "coinranking91b6bab1957bb16084164088c92d1e38e63b408ef85cdf33"
}

enum class TimePeriod(
    private val displayNameRes: Int,
    private val internalValue: String,
) : ItemBase {
    ONE_HOUR(R.string.one_hour_display, "1h"),
    THREE_HOURS(R.string.three_hours_display, "3h"),
    TWELVE_HOURS(R.string.twelve_hours_display, "12h"),
    TWENTY_FOUR_HOURS(R.string.twenty_four_hours_display, "24h"),
    SEVEN_DAYS(R.string.seven_days_display, "7d"),
    THIRTY_DAYS(R.string.thirty_days_display, "30d"),
    THREE_MONTHS(R.string.three_months_display, "3m"),
    ONE_YEAR(R.string.one_year_display, "1y"),
    THREE_YEARS(R.string.three_years_display, "3y"),
    FIVE_YEARS(R.string.five_years_display, "5y"),
    ;

    override fun getDisplayName(context: Context): String {
        return context.getString(displayNameRes)
    }

    override fun getInternalValue(): String {
        return internalValue
    }
}

enum class MarketCapInterval(private val displayName: String) {
    HOUR("hour"),
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    ;

    override fun toString(): String {
        return displayName
    }
}

enum class OrderBy(
    private val displayNameRes: Int,
    private val internalValue: String,
) : ItemBase {
    PRICE(R.string.price_display, "price"),
    MARKET_CAP(R.string.market_cap_display, "marketCap"),
    VOLUME_24H(R.string.volume_24h_display, "24hVolume"),
    CHANGE(R.string.change_display, "change"),
    LISTED_AT(R.string.listed_at_display, "listedAt"),
    ;

    override fun getDisplayName(context: Context): String {
        return context.getString(displayNameRes)
    }

    override fun getInternalValue(): String {
        return internalValue
    }
}

enum class OrderDirection(private val displayName: String) {
    DESC("desc"),
    ASC("asc"),
    ;

    override fun toString(): String {
        return displayName
    }
}
