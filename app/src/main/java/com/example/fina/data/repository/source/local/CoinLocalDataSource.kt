package com.example.fina.data.repository.source.local

import com.example.fina.data.repository.OnResultListener
import com.example.fina.data.repository.source.CoinDataSource

class CoinLocalDataSource private constructor(
    private val preferencesHelper: SharedPreferencesHelper,
) : CoinDataSource.Local {
    override fun getFavouriteCoins(listener: OnResultListener<List<String>>) {
        val persistedData: String? =
            preferencesHelper.get(SharedPreferencesHelper.PREF_FAVOURITE)
        persistedData?.let {
            val result = it.split(SharedPreferencesHelper.SEPARATOR_CHARACTER)
            listener.onSuccess(result)
        }
    }

    override fun addFavouriteCoin(
        uuid: String,
        listener: OnResultListener<Unit>,
    ) {
        val persistedData: String? = preferencesHelper.get(SharedPreferencesHelper.PREF_FAVOURITE)
        val favourites: MutableList<String> = mutableListOf()
        persistedData?.let {
            favourites.addAll((it.split(SharedPreferencesHelper.SEPARATOR_CHARACTER)))
        }
        favourites.add(uuid)
        preferencesHelper.save(
            SharedPreferencesHelper.PREF_FAVOURITE,
            favourites.joinToString(SharedPreferencesHelper.SEPARATOR_CHARACTER),
        )
    }

    override fun removeFavouriteCoin(
        uuid: String,
        listener: OnResultListener<Unit>,
    ) {
        val persistedData: String? = preferencesHelper.get(SharedPreferencesHelper.PREF_FAVOURITE)
        persistedData?.let {
            val favourites = it.split(SharedPreferencesHelper.SEPARATOR_CHARACTER).toMutableList()
            favourites.remove(uuid)
            preferencesHelper.save(
                SharedPreferencesHelper.PREF_FAVOURITE,
                favourites.joinToString(SharedPreferencesHelper.SEPARATOR_CHARACTER),
            )
        }
    }

    companion object {
        private var instance: CoinLocalDataSource? = null

        fun getInstance(preferencesHelper: SharedPreferencesHelper) =
            synchronized(this) {
                instance ?: CoinLocalDataSource(preferencesHelper).also { instance = it }
            }
    }
}
