package com.example.cryptocurency.ui.add_asset

import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.cryptocurency.data.models.Asset
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.databinding.ActivityAddAssetBinding
import com.example.cryptocurency.utils.*
import com.example.cryptocurency.utils.base.BaseActivity
import com.example.cryptocurency.utils.extension.showToast
import com.example.cryptocurency.utils.factory.PresenterFactory
import com.example.cryptocurency.utils.factory.SpinnerFactory

class AddAssetActivity : BaseActivity<ActivityAddAssetBinding>(ActivityAddAssetBinding::inflate),
    AddAssetActivityContract.View {

    private var mPresenter: AddAssetActivityContract.Presenter? = null
    private var coinsData = mutableListOf<Coin>()
    private var currentCoinPosition = 0

    override fun initView() {
        binding?.apply {
            btnBack.setOnClickListener {
                this@AddAssetActivity.finish()
            }
            btnAdd.setOnClickListener {
                handleAddAsset()
            }
        }
    }

    override fun initData() {
        mPresenter = PresenterFactory(this).createAddAssetActivityPresenter(this)
        mPresenter?.getCoinsData()
    }

    override fun getCoinsDataSuccessfully(coins: MutableList<Coin>) {
        val coinSymbolsData = ArrayList<String>()
        coinsData = coins
        coins.map {
            coinSymbolsData.add(it.symbol)
        }
        binding?.apply {
            spinnerCoin.apply {
                adapter = SpinnerFactory.createSpinnerAdapterFromArray(
                    this@AddAssetActivity,
                    coinSymbolsData
                )
                onItemSelectedListener = SpinnerFactory.createSpinnerListener(
                    {
                        currentCoinPosition = it
                    },
                    {},
                    false
                )
            }
            progressBar.visibility = View.GONE
            mainView.visibility = View.VISIBLE
        }
    }

    override fun getCoinsDataFailure(exception: Exception?) {
        this.showToast(exception?.message)
    }

    override fun addAssetSuccessfully() {
        this.showToast(ADD_SUCCESS)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                this.finish()
            },
            ADD_DELAY_TIME
        )
    }

    override fun addAssetFailure(exception: Exception?) {
        this.showToast(exception?.message)
        binding?.btnAdd?.apply {
            text = ADD
            isEnabled = true
        }
    }

    private fun handleAddAsset() {
        binding?.apply {
            btnAdd.apply {
                text = LOADING
                isEnabled = false
            }
            coinsData[currentCoinPosition].let { coin ->
                Asset().apply {
                    coinId = coin.id
                    coinName = coin.name
                    coinSymbol = coin.symbol
                    iconUrl = coin.iconUrl
                    inputPrice.text.toString().toDoubleOrNull()?.apply {
                        purchasePrice = this.toString()
                    } ?: run {
                        addAssetFailure(Exception(EXCEPTION_CANNOT_CALCULATE))
                        return
                    }
                    inputValue.text.toString().toDoubleOrNull()?.apply {
                        count = this.toString()
                    } ?: run {
                        addAssetFailure(Exception(EXCEPTION_CANNOT_CALCULATE))
                        return
                    }
                    mPresenter?.addAsset(this)
                }
            }
        }
    }
}
