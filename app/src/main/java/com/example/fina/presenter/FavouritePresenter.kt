package com.example.fina.presenter

import com.example.fina.data.model.Coin
import com.example.fina.data.repository.CoinRepository
import com.example.fina.data.repository.OnResultListener
import com.example.fina.screen.favourite.FavouriteContract
import com.example.fina.utils.ExtraParams
import com.example.fina.utils.OrderProperties

class FavouritePresenter internal constructor(
    private val mCoinRepository: CoinRepository?,
    private val mView: FavouriteContract.View?,
) :
    FavouriteContract.Presenter {
        override fun getCoins(
            params: ExtraParams,
            orderProperties: OrderProperties,
        ) {
            mCoinRepository?.getCoins(
                params = params,
                orderProperties = orderProperties,
                listener =
                    object : OnResultListener<List<Coin>> {
                        override fun onSuccess(data: List<Coin>) {
                            mView?.onGetCoinsSuccess(data)
                        }

                        override fun onFailure(exception: Exception?) {
                            exception?.let { mView?.onError(it) }
                        }
                    },
            )
        }
    }
