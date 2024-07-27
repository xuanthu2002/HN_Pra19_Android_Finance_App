package com.example.fina.data.repository.source.remote

import com.example.fina.data.model.Coin
import com.example.fina.data.model.PriceRecord
import com.example.fina.data.repository.OnResultListener
import com.example.fina.data.repository.source.CoinDataSource
import com.example.fina.data.repository.source.remote.fetchjson.GetJsonFromUrl
import com.example.fina.utils.Constant
import com.example.fina.utils.ExtraParams
import com.example.fina.utils.OrderProperties
import com.example.fina.utils.ResponseEntry

class CoinRemoteDataSource : CoinDataSource.Remote {
    override fun getCoins(
        params: ExtraParams,
        orderProperties: OrderProperties,
        listener: OnResultListener<List<Coin>>,
    ) {
        val url: StringBuilder = StringBuilder(Constant.BASE_URL_COINS)
        url.append("$params")
        url.append("&$orderProperties")
        GetJsonFromUrl(
            url.toString(),
            ResponseEntry.COINS,
            listener,
        )
    }

    override fun getCoinDetail(
        uuid: String,
        params: ExtraParams,
        listener: OnResultListener<Coin>,
    ) {
        val url = StringBuilder(Constant.BASE_URL_COIN_DETAIL)
        url.append(uuid)
        url.append("?$params")
        GetJsonFromUrl(
            url.toString(),
            ResponseEntry.COIN,
            listener,
        )
    }

    override fun getPriceHistory(
        uuid: String,
        params: ExtraParams,
        listener: OnResultListener<List<PriceRecord>>,
    ) {
        val url = StringBuilder(Constant.BASE_URL_COIN_DETAIL)
        url.append(uuid)
        url.append("/history?$params")
        GetJsonFromUrl(
            url.toString(),
            ResponseEntry.PRICE_HISTORY,
            listener,
        )
    }

    companion object {
        private var instance: CoinRemoteDataSource? = null

        fun getInstance() =
            synchronized(this) {
                instance ?: CoinRemoteDataSource().also { instance = it }
            }
    }
}
