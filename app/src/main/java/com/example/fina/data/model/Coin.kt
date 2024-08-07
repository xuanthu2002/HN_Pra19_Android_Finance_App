package com.example.fina.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coin(
    var uuid: String = "",
    var symbol: String = "",
    var name: String = "",
    var description: String = "",
    var iconUrl: String = "",
    var supply: Supply = Supply(),
    var volume24h: String = "0",
    var marketCap: String = "0",
    var price: String = "0",
    var btcPrice: String = "0",
    var priceAt: Long = 0,
    var change: String = "0",
    var rank: Int = 0,
    var sparkline: List<String> = emptyList(),
    var allTimeHigh: PriceRecord = PriceRecord(),
    var tier: Int = 0,
    var lowVolume: Boolean = false,
    var listedAt: Long = 0,
) : Parcelable
