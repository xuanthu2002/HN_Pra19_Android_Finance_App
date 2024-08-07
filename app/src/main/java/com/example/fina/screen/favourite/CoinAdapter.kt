package com.example.fina.screen.favourite

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fina.R
import com.example.fina.data.model.Coin
import com.example.fina.utils.OnItemRecyclerViewClickListener
import com.example.fina.utils.ext.loadImageCircleWithUrl

class CoinAdapter : RecyclerView.Adapter<CoinAdapter.ViewHolder>() {
    private val coins: MutableList<Coin> = mutableListOf()
    private lateinit var referenceCurrency: String
    private var onItemClickListener: OnItemRecyclerViewClickListener<Coin>? = null

    fun registerItemRecyclerViewClickListener(listener: OnItemRecyclerViewClickListener<Coin>?) {
        this.onItemClickListener = listener
    }

    fun setReferenceCurrency(referenceCurrency: String) {
        this.referenceCurrency = referenceCurrency
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_coin_layout, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return coins.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.binViewData(coins[position], referenceCurrency)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(coins: MutableList<Coin>?) {
        coins?.let {
            this.coins.clear()
            this.coins.addAll(coins)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(itemView: View, itemClickListener: OnItemRecyclerViewClickListener<Coin>?) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var mTextViewSymbol: TextView? = null
        private var mImageViewIcon: ImageView? = null
        private var mTextViewName: TextView? = null
        private var mTextViewChangeValue: TextView? = null
        private var mImageViewChangeIcon: ImageView? = null
        private var mTextViewPrice: TextView? = null
        private var mImageViewMore: ImageView? = null
        private var mTextViewMarketCap: TextView? = null
        private var mTextViewVol24h: TextView? = null
        private var mImageViewRefIconPrice: ImageView? = null
        private var mImageViewRefIconMarketCap: ImageView? = null
        private var mImageViewRefIconVol24h: ImageView? = null

        private var mCoinData: Coin? = null
        private var listener: OnItemRecyclerViewClickListener<Coin>? = null

        init {
            mTextViewSymbol = itemView.findViewById(R.id.textViewSymbol)
            mImageViewIcon = itemView.findViewById(R.id.imageViewIcon)
            mTextViewName = itemView.findViewById(R.id.textViewName)
            mTextViewChangeValue = itemView.findViewById(R.id.textViewChangeValue)
            mImageViewChangeIcon = itemView.findViewById(R.id.imageViewChangeIcon)
            mTextViewPrice = itemView.findViewById(R.id.textViewPrice)
            mImageViewMore = itemView.findViewById(R.id.imageViewMore)
            mTextViewMarketCap = itemView.findViewById(R.id.textViewMarketCap)
            mTextViewVol24h = itemView.findViewById(R.id.textViewVol24h)
            mImageViewRefIconPrice = itemView.findViewById(R.id.imageViewRefIconPrice)
            mImageViewRefIconMarketCap = itemView.findViewById(R.id.imageViewRefIconMarketCap)
            mImageViewRefIconVol24h = itemView.findViewById(R.id.imageViewRefIconVol4h)
            itemView.setOnClickListener(this)
            listener = itemClickListener
        }

        @SuppressLint("SetTextI18n")
        fun binViewData(
            coin: Coin,
            referenceCurrency: String,
        ) {
            coin.let {
                mTextViewSymbol?.text = it.symbol
                mImageViewIcon?.loadImageCircleWithUrl(it.iconUrl)
                mTextViewName?.text = it.name
                mTextViewChangeValue?.text = "${it.change}%"
                if (it.change.toFloat() < 0) {
                    mImageViewChangeIcon?.setImageResource(R.drawable.ic_down_arrow_red)
                    mTextViewChangeValue?.setTextColor(Color.RED)
                } else {
                    mImageViewChangeIcon?.setImageResource(R.drawable.ic_up_arrow_green)
                    mTextViewChangeValue?.setTextColor(Color.GREEN)
                }
                mImageViewRefIconPrice?.loadImageCircleWithUrl(it.iconUrl)
                mTextViewPrice?.text = formatNumber(it.price)
                mTextViewName?.text = it.name
                mImageViewRefIconMarketCap?.loadImageCircleWithUrl(it.iconUrl)
                mTextViewMarketCap?.text = formatNumber(it.marketCap)
                mImageViewRefIconVol24h?.loadImageCircleWithUrl(it.iconUrl)
                mTextViewVol24h?.text = formatNumber(it.volume24h)
                mCoinData = it
            }
        }

        private fun formatNumber(numberStr: String): String {
            val number = numberStr.toDouble()
            return when {
                number >= 1_000_000_000 -> String.format("%.1fB", number / 1_000_000_000.0)
                number >= 1_000_000 -> String.format("%.1fM", number / 1_000_000.0)
                number >= 1_000 -> String.format("%.1fK", number / 1_000.0)
                else -> String.format("%.1f", number)
            }
        }

        override fun onClick(view: View?) {
            listener?.onItemClick(mCoinData)
        }
    }
}
