package com.example.fina.utils

object Constant {
    const val BASE_URL_COINS = "https://api.coinranking.com/v2/coins?"
    const val BASE_URL_COIN_DETAIL = "https://api.coinranking.com/v2/coin/"
    const val USD_UUID = "yhjMzLPhuIDl"
    const val DEFAULT_LIMIT = 50
}

enum class TimePeriod(private val displayName: String) {
    ONE_HOUR("1h"),
    THREE_HOURS("3h"),
    TWELVE_HOURS("12h"),
    TWENTY_FOUR_HOURS("24h"),
    SEVEN_DAYS("7d"),
    THIRTY_DAYS("30d"),
    THREE_MONTHS("3m"),
    ONE_YEAR("1y"),
    THREE_YEARS("3y"),
    FIVE_YEARS("5y"),
    ;

    override fun toString(): String {
        return displayName
    }
}

enum class OrderBy(private val displayName: String) {
    PRICE("price"),
    MARKET_CAP("marketCap"),
    VOLUME_24H("24hVolume"),
    CHANGE("change"),
    LISTED_AT("listedAt"),
    ;

    override fun toString(): String {
        return displayName
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
