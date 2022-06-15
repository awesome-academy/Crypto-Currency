package com.example.cryptocurency.utils.factory

import android.content.Context
import com.example.cryptocurency.data.repository.AssetRepository
import com.example.cryptocurency.data.repository.CoinRepository
import com.example.cryptocurency.data.repository.source.local.AssetLocalDataSource
import com.example.cryptocurency.data.repository.source.local.CoinsLocalDataSource
import com.example.cryptocurency.data.repository.source.local.database.DatabaseHelper
import com.example.cryptocurency.data.repository.source.local.database.asset.AssetDAOImpl
import com.example.cryptocurency.data.repository.source.local.database.favorite.FavoriteCoinsDAOImpl
import com.example.cryptocurency.data.repository.source.remote.AssetRemoteDataSource
import com.example.cryptocurency.data.repository.source.remote.CoinsRemoteDataSource
import com.example.cryptocurency.ui.add_asset.AddAssetActivityContract
import com.example.cryptocurency.ui.add_asset.AddAssetActivityPresenter
import com.example.cryptocurency.ui.detail.DetailActivityContract
import com.example.cryptocurency.ui.detail.DetailActivityPresenter
import com.example.cryptocurency.ui.exchange.ExchangeActivityContract
import com.example.cryptocurency.ui.exchange.ExchangeActivityPresenter
import com.example.cryptocurency.ui.favorite_fragment.FavoriteFragmentContract
import com.example.cryptocurency.ui.favorite_fragment.FavoriteFragmentPresenter
import com.example.cryptocurency.ui.home_fragment.HomeFragmentContract
import com.example.cryptocurency.ui.home_fragment.HomeFragmentPresenter
import com.example.cryptocurency.ui.search.SearchActivityContract
import com.example.cryptocurency.ui.search.SearchActivityPresenter

class PresenterFactory(context: Context?) {

    private val coinRepository = CoinRepository.getInstance(
        CoinsRemoteDataSource.getInstance(),
        CoinsLocalDataSource.getInstance(
            FavoriteCoinsDAOImpl.getInstance(
                DatabaseHelper.getInstance(context)
            )
        )
    )

    private val assetRepository = AssetRepository.getInstance(
        AssetRemoteDataSource.getInstance(),
        AssetLocalDataSource.getInstance(
            AssetDAOImpl.getInstance(
                DatabaseHelper.getInstance(context)
            )
        ),
    )

    fun createHomeFragmentPresenter(view: HomeFragmentContract.View) = HomeFragmentPresenter(
        view,
        coinRepository
    )

    fun createFavoriteFragmentPresenter(view: FavoriteFragmentContract.View) = FavoriteFragmentPresenter(
        view,
        coinRepository
    )
    fun createDetailActivityPresenter(view: DetailActivityContract.View) = DetailActivityPresenter(
        view,
        coinRepository
    )

    fun createExchangeActivityPresenter(view: ExchangeActivityContract.View) = ExchangeActivityPresenter(
        view,
        coinRepository
    )

    fun createSearchActivityPresenter(view: SearchActivityContract.View) = SearchActivityPresenter(
        view,
        coinRepository
    )

    fun createAddAssetActivityPresenter(view: AddAssetActivityContract.View) = AddAssetActivityPresenter(
        view,
        coinRepository,
        assetRepository
    )
}
