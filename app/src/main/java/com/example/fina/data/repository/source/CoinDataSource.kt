package com.example.fina.data.repository.source

import com.example.fina.data.model.Coin
import com.example.fina.data.model.PriceRecord
import com.example.fina.data.repository.OnResultListener
import com.example.fina.utils.ExtraParams
import com.example.fina.utils.MarketCapInterval
import com.example.fina.utils.OrderProperties

interface CoinDataSource {
    interface Local {
        fun getFavouriteUuids(listener: OnResultListener<List<String>>)

        fun addFavouriteCoin(
            uuid: String,
            listener: OnResultListener<Unit>,
        )

        fun removeFavouriteCoin(
            uuid: String,
            listener: OnResultListener<Unit>,
        )
    }

    interface Remote {
        fun getCoins(
            params: ExtraParams = ExtraParams(),
            orderProperties: OrderProperties = OrderProperties(),
            listener: OnResultListener<List<Coin>>,
        )

        fun getCoinsWithUuids(
            uuids: List<String>,
            params: ExtraParams = ExtraParams(),
            orderProperties: OrderProperties = OrderProperties(),
            listener: OnResultListener<List<Coin>>,
        )

        fun getPriceHistory(
            uuid: String,
            params: ExtraParams,
            listener: OnResultListener<List<PriceRecord>>,
        )

        fun getMarketCapHistory(
            uuid: String,
            interval: MarketCapInterval = MarketCapInterval.DAY,
            listener: OnResultListener<List<PriceRecord>>,
        )
    }
}
