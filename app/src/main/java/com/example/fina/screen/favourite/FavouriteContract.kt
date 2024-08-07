package com.example.fina.screen.favourite

import com.example.fina.data.model.Coin
import com.example.fina.utils.ExtraParams
import com.example.fina.utils.OrderProperties

interface FavouriteContract {
    interface View {
        fun onGetCoinsSuccess(coins: List<Coin>)

        fun onError(exception: Exception)
    }

    interface Presenter {
        fun getCoins(
            params: ExtraParams,
            orderProperties: OrderProperties,
        )
    }
}
