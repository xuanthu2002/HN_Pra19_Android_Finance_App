package com.example.fina.utils

class ExtraParams(
    private val referenceCurrencyUuid: String = Constant.USD_UUID,
    private val timePeriod: TimePeriod = TimePeriod.TWENTY_FOUR_HOURS,
) {
    override fun toString(): String {
        return "referenceCurrencyUuid=$referenceCurrencyUuid&timePeriod=$timePeriod"
    }
}

class OrderProperties(
    private val orderBy: OrderBy = OrderBy.MARKET_CAP,
    private val orderDirection: OrderDirection = OrderDirection.DESC,
    private val limit: Int = Constant.DEFAULT_LIMIT,
    private val offset: Int = 0,
) {
    override fun toString(): String {
        return "orderBy=$orderBy&orderDirection=$orderDirection&limit=$limit&offset=$offset"
    }
}
