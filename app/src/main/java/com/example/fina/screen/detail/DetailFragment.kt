package com.example.fina.screen.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import com.example.fina.R
import com.example.fina.data.model.Coin
import com.example.fina.data.model.PriceRecord
import com.example.fina.data.repository.CoinRepository
import com.example.fina.data.repository.source.local.CoinLocalDataSource
import com.example.fina.data.repository.source.local.SharedPreferencesHelper
import com.example.fina.data.repository.source.remote.CoinRemoteDataSource
import com.example.fina.databinding.FragmentDetailBinding
import com.example.fina.utils.Constant
import com.example.fina.utils.ExtraParams
import com.example.fina.utils.base.BaseFragment
import com.example.fina.utils.changeImageToPng
import com.example.fina.utils.ext.goBackFragment
import com.example.fina.utils.ext.loadImageWithUrl
import com.example.fina.utils.ext.notNull
import com.example.fina.utils.ext.setBackgroundBasedOnValue
import com.example.fina.utils.ext.setIconChange
import com.example.fina.utils.ext.showWithDelay
import com.example.fina.utils.ext.updateBackgroundByOnClick
import com.example.fina.utils.ext.updateBackgroundTimeByOnClick
import com.example.fina.utils.ext.updateTextColor
import com.example.fina.utils.formatChangeValue
import com.example.fina.utils.toMarketCapInterval
import com.example.fina.utils.toTimePeriod

class DetailFragment : BaseFragment<FragmentDetailBinding>(), DetailContract.View {
    private var inforSelected: String = "Price"
    private var timeSelected: String = "24h"
    private var selectedId: Int = R.id.price
    private var selectedTimeId: Int = R.id.menu_24h
    private var mCoin: Coin? = null
    private lateinit var mDetailPresenter: DetailPresenter

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater)
    }

    override fun initData() {
        loadCoinData()
        setupDefaultView()
    }

    private fun loadCoinData() {
        arguments?.run {
            mCoin = getParcelable(ARGUMENT_COIN)
            Log.i("TAG1", "loadCoinData: $mCoin")
        }
        mCoin?.notNull {
            viewBinding.apply {
                symbol.text = it.symbol
                nameCoin.text = it.name
                iconCoin.loadImageWithUrl(changeImageToPng(it.iconUrl))
                valueChanger.text = getString(R.string.change_value, formatChangeValue(it.change))
                blockChange.setBackgroundBasedOnValue(it.change.toDouble())
                iconChange.setIconChange(it.change.toDouble())
                valueVol24h.text = it.volume24h
                valueMarketCap.text = it.marketCap
                valueCirculating.text = it.supply.circulating
                valueTotalSupply.text = it.supply.total
                valueMaxSupply.text = it.supply.max
                description.text = it.description
            }
        }
    }

    private fun setupDefaultView() {
        val context = requireContext()
        val preferencesHelper = SharedPreferencesHelper.getInstance(context)
        mDetailPresenter =
            DetailPresenter(
                CoinRepository.getInstance(
                    CoinRemoteDataSource.getInstance(),
                    CoinLocalDataSource.getInstance(preferencesHelper),
                ),
            )
        mDetailPresenter.setView(this)
        mCoin?.let {
            showDataChart()
        }
    }

    override fun initView() {
        viewBinding.btnBack.setOnClickListener { goBackFragment() }
        setupButtonClickListeners()
        updateMenuSelection()
        updateTimeSelection()
    }

    private fun setupButtonClickListeners() {
        viewBinding.apply {
            listOf(price, marketCap).forEach { button ->
                button.setOnClickListener { onMenuButtonClick(it.id) }
            }
            listOf(menu5y, menu3y, menu1y, menu3m, menu1m, menu7d, menu24h, menu1h).forEach { button ->
                button.setOnClickListener { onTimeButtonClick(it.id) }
            }
        }
    }

    private fun onMenuButtonClick(id: Int) {
        selectedId = id
        updateMenuSelection()
        viewBinding.infoBox.visibility = View.GONE
        val selectedText =
            when (id) {
                R.id.price -> viewBinding.priceText.text
                R.id.market_cap -> viewBinding.marketCapText.text
                else -> ""
            }
        inforSelected = selectedText.toString()
        showDataChart()
    }

    private fun onTimeButtonClick(id: Int) {
        selectedTimeId = id
        updateTimeSelection()
        viewBinding.infoBox.visibility = View.GONE
        val selectedText =
            when (id) {
                R.id.menu_5y -> viewBinding.menu5yText.text
                R.id.menu_3y -> viewBinding.menu3yText.text
                R.id.menu_1y -> viewBinding.menu1yText.text
                R.id.menu_3m -> viewBinding.menu3mText.text
                R.id.menu_1m -> viewBinding.menu1mText.text
                R.id.menu_7d -> viewBinding.menu7dText.text
                R.id.menu_24h -> viewBinding.menu24hText.text
                R.id.menu_1h -> viewBinding.menu1hText.text
                else -> ""
            }
        timeSelected = selectedText.toString()
        showDataChart()
    }

    private fun updateMenuSelection() {
        viewBinding.apply {
            price.updateBackgroundByOnClick(selectedId, R.id.price)
            marketCap.updateBackgroundByOnClick(selectedId, R.id.market_cap)
            priceText.updateTextColor(selectedId, R.id.price)
            marketCapText.updateTextColor(selectedId, R.id.market_cap)
        }
    }

    private fun updateTimeSelection() {
        viewBinding.apply {
            listOf(
                menu5y to menu5yText,
                menu3y to menu3yText,
                menu1y to menu1yText,
                menu3m to menu3mText,
                menu1m to menu1mText,
                menu7d to menu7dText,
                menu24h to menu24hText,
                menu1h to menu1hText,
            ).forEach { (layout, textView) ->
                layout.updateBackgroundTimeByOnClick(selectedTimeId, layout.id)
                textView.updateTextColor(selectedTimeId, layout.id)
            }
        }
    }

    private fun getDataBySelected() {
        if (inforSelected == "Price") {
            val timePeriod = timeSelected.toTimePeriod()
            timePeriod?.let { mCoin?.let { mDetailPresenter.getPrices(it.uuid, ExtraParams(Constant.USD_UUID, timePeriod)) } }
        } else {
            val interval = timeSelected.toMarketCapInterval()
            if (interval == null) {
                viewBinding.lineChart.setNoDataText(getString(R.string.notfound))
                viewBinding.lineChart.clear()
            } else {
                mCoin?.let { mDetailPresenter.getMartketCap(it.uuid, interval) }
            }
        }
    }

    private fun showDataChart() {
        viewBinding.loadingBar.showWithDelay(viewBinding.lineChart) {
            getDataBySelected()
        }
    }

    override fun onGetPricesSuccess(prices: List<PriceRecord>) {
        val lineData = mDetailPresenter.processPriceData(requireContext(), prices)
        viewBinding.lineChart.data = lineData
        viewBinding.setupChartAppearance(requireContext())
        viewBinding.lineChart.setOnChartValueSelectedListener(viewBinding.createOnChartValueSelectedListener(prices))
        viewBinding.lineChart.invalidate()
    }

    override fun onError(exception: Exception?) {
        // Handle the error
    }

    companion object {
        private const val ARGUMENT_COIN = "ARGUMENT_COIN"

        fun newInstance(coin: Coin?) =
            DetailFragment().apply {
                arguments = bundleOf(ARGUMENT_COIN to coin)
            }
    }
}
