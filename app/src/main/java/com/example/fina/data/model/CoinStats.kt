package com.example.fina.data.model

data class CoinStats(
    var total: Int = 0,
    var totalCoins: Int = 0,
    var totalMarkets: Int = 0,
    var totalExchanges: Int = 0,
    var totalMarketCap: String = "",
    var total24hVolume: String = "",
)
