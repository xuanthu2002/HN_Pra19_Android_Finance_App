package com.example.fina.data.repository.source.local

import com.example.fina.data.model.Coin
import com.example.fina.data.repository.OnResultListener
import com.example.fina.data.repository.source.CoinDataSource

class CoinLocalDataSource : CoinDataSource.Local {
    override fun getLocalCoins(listener: OnResultListener<List<Coin>>) {
        TODO("Not yet implemented")
    }

    companion object {
        private var instance: CoinLocalDataSource? = null

        fun getInstance() =
            synchronized(this) {
                instance ?: CoinLocalDataSource().also { instance = it }
            }
    }
}
