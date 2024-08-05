package com.example.fina.screen.detail

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.fina.R
import com.example.fina.data.model.PriceRecord
import com.example.fina.data.repository.CoinRepository
import com.example.fina.data.repository.OnResultListener
import com.example.fina.utils.ExtraParams
import com.example.fina.utils.MarketCapInterval
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class DetailPresenter internal constructor(private val coinRepository: CoinRepository) :
    DetailContract.Presenter {
        private var mView: DetailContract.View? = null

        override fun getPrices(
            uuid: String,
            params: ExtraParams,
        ) {
            coinRepository.getPriceHistory(
                uuid,
                params,
                object : OnResultListener<List<PriceRecord>> {
                    override fun onSuccess(data: List<PriceRecord>) {
                        mView?.onGetPricesSuccess(data)
                    }

                    override fun onFailure(exception: Exception?) {
                        Log.i(TAG, "onFailure: ")
                    }
                },
            )
        }

        override fun getMartketCap(
            uuid: String,
            martketcapinterval: MarketCapInterval,
        ) {
            coinRepository.getMarketCapHistory(
                uuid,
                martketcapinterval,
                object : OnResultListener<List<PriceRecord>> {
                    override fun onSuccess(data: List<PriceRecord>) {
                        mView?.onGetPricesSuccess(data)
                    }

                    override fun onFailure(exception: Exception?) {
                        /* no-op */
                    }
                },
            )
        }

        override fun processPriceData(
            context: Context,
            prices: List<PriceRecord>,
        ): LineData {
            val reversedHistoryItems = prices.reversed()
            val entries =
                reversedHistoryItems.mapIndexed { index, item ->
                    val price = item.price.toFloatOrNull() ?: 0f
                    Entry(index.toFloat(), price)
                }
            val dataSet = LineDataSet(entries, "")
            dataSet.lineWidth = 0.2f
            dataSet.color = Color.BLUE
            dataSet.setDrawFilled(true)
            dataSet.fillColor = ContextCompat.getColor(context, R.color.light_blue)
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)

            return LineData(dataSet)
        }

        override fun onStart() {
            /* no-op */
        }

        override fun onStop() {
            /* no-op */
        }

        override fun setView(view: DetailContract.View?) {
            this.mView = view
        }
    }
