package com.example.cryptocurency.utils.factory

import android.content.Context
import com.example.cryptocurency.data.repository.CoinRepository
import com.example.cryptocurency.data.repository.source.local.CoinsLocalDataSource
import com.example.cryptocurency.data.repository.source.local.database.DatabaseHelper
import com.example.cryptocurency.data.repository.source.local.database.favorite.FavoriteCoinsDAOImpl
import com.example.cryptocurency.data.repository.source.remote.CoinsRemoteDataSource
import com.example.cryptocurency.ui.home_fragment.HomeFragmentContract
import com.example.cryptocurency.ui.home_fragment.HomeFragmentPresenter

class PresenterFactory(context: Context?) {
    private val coinRepository = CoinRepository.getInstance(
        CoinsRemoteDataSource.getInstance(),
        CoinsLocalDataSource.getInstance(
            FavoriteCoinsDAOImpl.getInstance(
                DatabaseHelper.getInstance(context)
            )
        )
    )

    fun createHomeFragmentPresenter(view: HomeFragmentContract.View) = HomeFragmentPresenter(
        view,
        coinRepository
    )
}
