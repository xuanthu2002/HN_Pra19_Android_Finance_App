package com.example.fina.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Supply(
    var confirmed: Boolean = false,
    var supplyAt: Long = 0,
    var max: String = "0",
    var total: String = "0",
    var circulating: String = "0",
) : Parcelable
