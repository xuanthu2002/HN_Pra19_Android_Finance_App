package com.example.fina.data.repository.source

import com.example.fina.data.model.Coin
import com.example.fina.data.model.PriceRecord
import com.example.fina.data.repository.OnResultListener
import com.example.fina.utils.ExtraParams
import com.example.fina.utils.OrderProperties

interface CoinDataSource {
    interface Local {
        fun getLocalCoins(listener: OnResultListener<List<Coin>>)
    }

    interface Remote {
        fun getCoins(
            params: ExtraParams = ExtraParams(),
            orderProperties: OrderProperties = OrderProperties(),
            listener: OnResultListener<List<Coin>>,
        )

        fun getCoinDetail(
            uuid: String,
            params: ExtraParams,
            listener: OnResultListener<Coin>,
        )

        fun getPriceHistory(
            uuid: String,
            params: ExtraParams,
            listener: OnResultListener<List<PriceRecord>>,
        )
    }
}
