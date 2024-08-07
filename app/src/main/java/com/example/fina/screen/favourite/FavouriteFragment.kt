package com.example.fina.screen.favourite

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.example.fina.R
import com.example.fina.data.model.Coin
import com.example.fina.data.repository.CoinRepository
import com.example.fina.data.repository.source.local.CoinLocalDataSource
import com.example.fina.data.repository.source.local.SharedPreferencesHelper
import com.example.fina.data.repository.source.remote.CoinRemoteDataSource
import com.example.fina.databinding.FragmentFavouriteBinding
import com.example.fina.presenter.FavouritePresenter
import com.example.fina.screen.detail.DetailFragment
import com.example.fina.utils.Constant
import com.example.fina.utils.ExtraParams
import com.example.fina.utils.OnItemRecyclerViewClickListener
import com.example.fina.utils.OrderBy
import com.example.fina.utils.OrderProperties
import com.example.fina.utils.TimePeriod
import com.example.fina.utils.base.BaseFragment
import com.example.fina.utils.base.ItemBase
import com.example.fina.utils.ext.replaceFragment
import com.google.android.material.button.MaterialButton

class FavouriteFragment :
    BaseFragment<FragmentFavouriteBinding>(),
    FavouriteContract.View,
    View.OnClickListener {
    private lateinit var mFavouritePresenter: FavouriteContract.Presenter
    private val mCoinAdapter: CoinAdapter by lazy { CoinAdapter() }

    private var mCurrentRefCurrency: String = Constant.USD_UUID
    private var mCurrentTimePeriod: TimePeriod = TimePeriod.TWENTY_FOUR_HOURS
    private var mCurrentOrderBy: OrderBy = OrderBy.MARKET_CAP

    private lateinit var mButtonChooseRefCurrency: MaterialButton
    private lateinit var mButtonChooseTimePeriod: MaterialButton
    private lateinit var mButtonChooseOrderBy: MaterialButton

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentFavouriteBinding {
        return FragmentFavouriteBinding.inflate(inflater)
    }

    override fun initData() {
        mFavouritePresenter =
            FavouritePresenter(
                CoinRepository.getInstance(
                    CoinRemoteDataSource.getInstance(),
                    CoinLocalDataSource.getInstance(
                        SharedPreferencesHelper.getInstance(requireContext()),
                    ),
                ),
                this,
            )

        mButtonChooseRefCurrency.text = mCurrentRefCurrency
        mButtonChooseTimePeriod.text = mCurrentTimePeriod.getDisplayName(requireContext())
        mButtonChooseOrderBy.text = mCurrentOrderBy.getDisplayName(requireContext())

        val params =
            ExtraParams(
                referenceCurrencyUuid = mCurrentRefCurrency,
                timePeriod = mCurrentTimePeriod,
            )
        val orderProperties = OrderProperties(orderBy = mCurrentOrderBy)
        mFavouritePresenter.getCoins(
            params,
            orderProperties,
        )
    }

    override fun initView() {
        mCoinAdapter.registerItemRecyclerViewClickListener(
            object :
                OnItemRecyclerViewClickListener<Coin> {
                override fun onItemClick(item: Coin?) {
                    replaceFragment(
                        R.id.layoutContainer,
                        DetailFragment.newInstance(item),
                        addToBackStack = true,
                    )
                }
            },
        )
        mCoinAdapter.setReferenceCurrency("$")

        viewBinding.recyclerView.apply {
            adapter = mCoinAdapter
        }

        mButtonChooseRefCurrency = viewBinding.buttonChooseRefCurrency
        mButtonChooseTimePeriod = viewBinding.buttonChooseTimePeriod
        mButtonChooseOrderBy = viewBinding.buttonChooseOrderBy

//        buttonChooseRefCurrency.setOnClickListener(this)
        mButtonChooseTimePeriod.setOnClickListener(this)
        mButtonChooseOrderBy.setOnClickListener(this)
    }

    override fun onGetCoinsSuccess(coins: List<Coin>) {
        mCoinAdapter.updateData(coins.toMutableList())
    }

    override fun onError(exception: Exception) {
        Log.e("TAG", "onError: $exception")
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            viewBinding.buttonChooseRefCurrency.id -> onClickChooseReferenceCurrency()
            viewBinding.buttonChooseTimePeriod.id -> onClickChooseTimePeriod()
            viewBinding.buttonChooseOrderBy.id -> onClickChooseOrderBy()
        }
    }

    private fun onClickChooseOrderBy() {
        showBottomSheetChooseOrderBy()
    }

    private fun onClickChooseTimePeriod() {
        showBottomSheetChooseTimePeriod()
    }

    private fun onClickChooseReferenceCurrency() {
        showBottomSheetChooseReferenceCurrency()
    }

    private fun showBottomSheetChooseReferenceCurrency() {
//        val bottomSheet = MyBottomSheetDialogFragment(
//            "Choose currency",
//
//        )
    }

    private fun showBottomSheetChooseTimePeriod() {
        val bottomSheet =
            MyBottomSheetDialogFragment(
                getString(R.string.choose_period),
                TimePeriod.values().toList(),
                mCurrentTimePeriod,
                onItemSelected =
                    object : OnItemRecyclerViewClickListener<ItemBase> {
                        override fun onItemClick(item: ItemBase?) {
                            if (item != null) {
                                mButtonChooseTimePeriod.text =
                                    item.getDisplayName(requireContext())
                                mCurrentTimePeriod = item as TimePeriod
                            }
                            refresh()
                        }
                    },
            )

        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    private fun showBottomSheetChooseOrderBy() {
        val bottomSheet =
            MyBottomSheetDialogFragment(
                getString(R.string.sort_by),
                OrderBy.values().toList(),
                mCurrentOrderBy,
                onItemSelected =
                    object : OnItemRecyclerViewClickListener<ItemBase> {
                        override fun onItemClick(item: ItemBase?) {
                            if (item != null) {
                                mButtonChooseOrderBy.text =
                                    item.getDisplayName(requireContext())
                                mCurrentOrderBy = item as OrderBy
                            }
                            refresh()
                        }
                    },
            )

        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    private fun refresh() {
        val params =
            ExtraParams(
                referenceCurrencyUuid = mCurrentRefCurrency,
                timePeriod = mCurrentTimePeriod,
            )
        val orderProperties = OrderProperties(orderBy = mCurrentOrderBy)
        mFavouritePresenter.getCoins(
            params,
            orderProperties,
        )
    }
}
