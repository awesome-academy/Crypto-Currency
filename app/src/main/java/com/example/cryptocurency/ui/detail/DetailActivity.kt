package com.example.cryptocurency.ui.detail

import android.text.Html
import com.example.cryptocurency.R
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.databinding.ActivityDetailBinding
import com.example.cryptocurency.utils.COIN_EXTRA
import com.example.cryptocurency.utils.base.BaseActivity
import com.example.cryptocurency.utils.extension.*
import com.example.cryptocurency.utils.factory.PresenterFactory

class DetailActivity : BaseActivity<ActivityDetailBinding>(ActivityDetailBinding::inflate),
    DetailActivityContract.View {

    private var mPresenter: DetailActivityContract.Presenter? = null
    private var mCoin: Coin? = null
    private var isFavorite: Boolean? = null

    override fun initView() {
        binding?.apply {
            setSupportActionBar(toolBar)
            btnBack.setOnClickListener {
                this@DetailActivity.finish()
            }
            btnFavorite.setOnClickListener {
                isFavorite?.let {
                    mCoin?.apply {
                        mPresenter?.handleFavoriteClick(this, it)
                    }
                }
            }
            txtCoinPrice.isSelected = true
        }
    }

    override fun initData() {
        mPresenter = PresenterFactory(this).createDetailActivityPresenter(this)
        mCoin = intent.getParcelableExtra(COIN_EXTRA) as Coin?
        setViewData(mCoin)
        mCoin?.let { coin ->
            mPresenter?.apply {
                checkFavorite(coin)
                getCoinDetail(coin)
            }
        }
    }

    override fun getCoinDetailSuccessfully(coin: Coin) {
        updateViewData(coin)
    }

    override fun getCoinDetailFailure(exception: Exception?) {
        this.showToast(exception?.message)
    }

    override fun checkFavoriteSuccessfully(isFavorite: Boolean) {
        updateFavoriteButton(isFavorite)
    }

    override fun checkFavoriteFailure(exception: Exception?) {
        this.showToast(exception?.message)
        isFavorite = null
    }

    override fun updateFavoriteSuccessfully(isFavorite: Boolean) {
        updateFavoriteButton(isFavorite)
    }

    override fun updateFavoriteFailure(exception: Exception?) {
        this.showToast(exception?.message)
    }

    private fun setViewData(coin: Coin?) {
        coin?.apply {
            binding?.apply {
                toolBarTitle.text = name
                txtCoinSymbol.text = symbol
                txtCoinRank.text = rank.toString().getCoinRank()
                imgCoin.loadImageSVG(iconUrl, R.drawable.img_default_coin)
                txtCoinPrice.text = price.getCoinPrice()
                txtCoinChange.apply {
                    text = chance.getCoinChange()
                    chance.toDoubleOrNull()?.let {
                        setBackgroundResource(
                            if (it >= 0) R.drawable.background_increase else R.drawable.background_decrease
                        )
                    }
                }
            }
        }
    }

    private fun updateViewData(coin: Coin?) {
        mCoin = coin
        coin?.apply {
            coinDetail.apply {
                binding?.apply {
                    txtCoinDescription.text =
                        Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT)
                    txtCoinMarketCap.text = marketCap.getCoinPrice()
                    txtCoin24hVolume.text = n24hVolume.getCoinPrice()
                    txtCoinNumMarket.text = numberOfMarkets.toString()
                    txtCoinNumExchange.text = numberOfExchanges.toString()
                    txtCoinBtcPrice.text = btcPrice
                    txtCoinWebsiteUrl.text = websiteUrl
                    viewChart.createCoinChart(sparkline, color)
                }
            }
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        this.isFavorite = isFavorite
        binding?.apply {
            btnFavorite.apply {
                setImageResource(
                    if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                )
            }
        }
    }
}
