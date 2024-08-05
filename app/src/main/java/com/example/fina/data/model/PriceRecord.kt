package com.example.fina.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PriceRecord(
    var price: String = "0",
    var timestamp: Long = 0,
) : Parcelable
