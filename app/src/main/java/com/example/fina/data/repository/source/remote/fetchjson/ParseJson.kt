package com.example.fina.data.repository.source.remote.fetchjson

import com.example.fina.data.model.Coin
import com.example.fina.data.model.CoinStats
import com.example.fina.data.model.PriceRecord
import com.example.fina.data.model.Supply
import org.json.JSONArray
import org.json.JSONObject

object ParseJson {
    fun coinParseJson(jsonObject: JSONObject) =
        Coin().apply {
            uuid = jsonObject.getString("uuid")
            symbol = jsonObject.getString("symbol")
            name = jsonObject.getString("name")
            iconUrl = jsonObject.optString("iconUrl")
            marketCap = jsonObject.getString("marketCap")
            price = jsonObject.getString("price")
            listedAt = jsonObject.getLong("listedAt")
            tier = jsonObject.getInt("tier")
            change = jsonObject.getString("change")
            rank = jsonObject.getInt("rank")
            sparkline = parseSparkline(jsonObject.optJSONArray("sparkline"))
            lowVolume = jsonObject.optBoolean("lowVolume")
            volume24h = jsonObject.optString("24hVolume")
            btcPrice = jsonObject.getString("btcPrice")
            supply = supplyParseJson(jsonObject.optJSONObject("supply"))
            allTimeHigh = priceRecordParseJson(jsonObject.optJSONObject("allTimeHigh"))
        }

    fun coinStatsParseJson(jsonObject: JSONObject): CoinStats {
        return CoinStats().apply {
            total = jsonObject.getInt("total")
            totalCoins = jsonObject.getInt("totalCoins")
            totalMarkets = jsonObject.getInt("totalMarkets")
            totalExchanges = jsonObject.getInt("totalExchanges")
            totalMarketCap = jsonObject.getString("totalMarketCap")
            total24hVolume = jsonObject.getString("total24hVolume")
        }
    }

    private fun parseSparkline(jsonArray: JSONArray?): List<String> {
        val sparkline = mutableListOf<String>()
        jsonArray?.let {
            for (i in 0 until it.length()) {
                sparkline.add(it.optString(i))
            }
        }
        return sparkline
    }

    private fun supplyParseJson(jsonObject: JSONObject?): Supply {
        val supply = Supply()
        jsonObject?.let {
            supply.confirmed = it.getBoolean("confirmed")
            supply.supplyAt = it.getLong("supplyAt")
            supply.max = it.getString("max")
            supply.total = it.getString("total")
            supply.circulating = it.getString("circulating")
        }
        return supply
    }

    fun priceRecordParseJson(jsonObject: JSONObject?): PriceRecord {
        val priceRecord = PriceRecord()
        jsonObject?.let {
            priceRecord.price = it.getString("price")
            priceRecord.timestamp = it.getLong("timestamp")
        }
        return priceRecord
    }
}
