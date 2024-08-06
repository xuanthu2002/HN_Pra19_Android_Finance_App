package com.example.fina.data.repository

import com.example.fina.data.model.Coin
import com.example.fina.data.model.PriceRecord
import com.example.fina.data.repository.source.CoinDataSource
import com.example.fina.utils.ExtraParams
import com.example.fina.utils.MarketCapInterval
import com.example.fina.utils.OrderProperties

class CoinRepository private constructor(
    private val remote: CoinDataSource.Remote,
    private val local: CoinDataSource.Local,
) : CoinDataSource.Remote, CoinDataSource.Local {
    override fun getCoins(
        params: ExtraParams,
        orderProperties: OrderProperties,
        listener: OnResultListener<List<Coin>>,
    ) {
        remote.getCoins(
            params,
            orderProperties,
            listener,
        )
    }

    override fun getCoinsWithUuids(
        uuids: List<String>,
        params: ExtraParams,
        orderProperties: OrderProperties,
        listener: OnResultListener<List<Coin>>,
    ) {
        remote.getCoinsWithUuids(uuids, params, orderProperties, listener)
    }

    override fun getPriceHistory(
        uuid: String,
        params: ExtraParams,
        listener: OnResultListener<List<PriceRecord>>,
    ) {
        remote.getPriceHistory(uuid, params, listener)
    }

    override fun getMarketCapHistory(
        uuid: String,
        interval: MarketCapInterval,
        listener: OnResultListener<List<PriceRecord>>,
    ) {
        remote.getMarketCapHistory(uuid, interval, listener)
    }

    override fun getFavouriteUuids(listener: OnResultListener<List<String>>) {
        local.getFavouriteUuids(listener)
    }

    override fun addFavouriteCoin(
        uuid: String,
        listener: OnResultListener<Unit>,
    ) {
        local.addFavouriteCoin(uuid, listener)
    }

    override fun removeFavouriteCoin(
        uuid: String,
        listener: OnResultListener<Unit>,
    ) {
        local.removeFavouriteCoin(uuid, listener)
    }

    companion object {
        private var instance: CoinRepository? = null

        fun getInstance(
            remote: CoinDataSource.Remote,
            local: CoinDataSource.Local,
        ) = synchronized(this) {
            instance ?: CoinRepository(remote, local).also { instance = it }
        }
    }
}
