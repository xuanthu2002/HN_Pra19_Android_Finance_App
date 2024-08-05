package com.example.fina.screen.detail

import android.content.Context
import com.example.fina.data.model.PriceRecord
import com.example.fina.utils.ExtraParams
import com.example.fina.utils.MarketCapInterval
import com.example.fina.utils.base.BasePresenter
import com.github.mikephil.charting.data.LineData

class DetailContract {
    /**
     * View
     */
    interface View {
        fun onGetPricesSuccess(prices: List<PriceRecord>)

        fun onError(exception: Exception?)
    }

    /**
     * Presenter
     */
    interface Presenter : BasePresenter<View> {
        fun getPrices(
            uuid: String,
            params: ExtraParams,
        )

        fun getMartketCap(
            uuid: String,
            martketcapinterval: MarketCapInterval,
        )

        fun processPriceData(
            context: Context,
            prices: List<PriceRecord>,
        ): LineData
    }
}
