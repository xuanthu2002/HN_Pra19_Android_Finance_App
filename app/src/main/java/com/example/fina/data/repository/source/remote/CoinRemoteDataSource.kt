package com.example.fina.data.repository.source.remote

import com.example.fina.data.model.Coin
import com.example.fina.data.model.PriceRecord
import com.example.fina.data.repository.OnResultListener
import com.example.fina.data.repository.source.CoinDataSource
import com.example.fina.data.repository.source.remote.fetchjson.GetJsonFromUrl
import com.example.fina.utils.Constant
import com.example.fina.utils.ExtraParams
import com.example.fina.utils.MarketCapInterval
import com.example.fina.utils.OrderProperties
import com.example.fina.utils.ResponseEntry

class CoinRemoteDataSource : CoinDataSource.Remote {
    override fun getCoins(
        params: ExtraParams,
        orderProperties: OrderProperties,
        listener: OnResultListener<List<Coin>>,
    ) {
        val url = "${Constant.BASE_URL_COINS}?$params&$orderProperties"
        GetJsonFromUrl(
            url,
            ResponseEntry.COINS,
            listener,
        )
    }

    override fun getCoinsWithUuids(
        uuids: List<String>,
        params: ExtraParams,
        orderProperties: OrderProperties,
        listener: OnResultListener<List<Coin>>,
    ) {
        if (uuids.isEmpty()) {
            listener.onSuccess(emptyList())
        } else {
            val url = StringBuilder("${Constant.BASE_URL_COINS}?$params&$orderProperties")
            uuids.forEach {
                url.append("&uuids[]=$it")
            }
            GetJsonFromUrl(url.toString(), ResponseEntry.COINS, listener)
        }
    }

    override fun getPriceHistory(
        uuid: String,
        params: ExtraParams,
        listener: OnResultListener<List<PriceRecord>>,
    ) {
        val url = "${Constant.BASE_URL_COIN_DETAIL}$uuid/history?$params"
        GetJsonFromUrl(
            url,
            ResponseEntry.PRICE_HISTORY,
            listener,
        )
    }

    override fun getMarketCapHistory(
        uuid: String,
        interval: MarketCapInterval,
        listener: OnResultListener<List<PriceRecord>>,
    ) {
        val url = "${Constant.BASE_URL_COIN_DETAIL}$uuid/marketCaps?interval=$interval"
        GetJsonFromUrl(url, ResponseEntry.PRICE_HISTORY, listener)
    }

    companion object {
        private var instance: CoinRemoteDataSource? = null

        fun getInstance() =
            synchronized(this) {
                instance ?: CoinRemoteDataSource().also { instance = it }
            }
    }
}
