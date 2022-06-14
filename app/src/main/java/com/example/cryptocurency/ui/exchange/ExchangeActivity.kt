package com.example.cryptocurency.ui.exchange

import android.text.Html
import android.view.View
import com.example.cryptocurency.R
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.databinding.ActivityExchangeBinding
import com.example.cryptocurency.utils.EXCEPTION_CANNOT_CALCULATE
import com.example.cryptocurency.utils.base.BaseActivity
import com.example.cryptocurency.utils.extension.createCompareChart
import com.example.cryptocurency.utils.extension.setTextColorFromResource
import com.example.cryptocurency.utils.extension.showToast
import com.example.cryptocurency.utils.factory.PresenterFactory
import com.example.cryptocurency.utils.factory.SpinnerFactory

class ExchangeActivity : BaseActivity<ActivityExchangeBinding>(ActivityExchangeBinding::inflate),
    ExchangeActivityContract.View {

    private var mPresenter: ExchangeActivityContract.Presenter? = null
    private var positionFrom: Int = 0
    private var positionTo: Int = 0
    private var coinList = mutableListOf<Coin>()
    private var coinSymbolList = ArrayList<String>()

    override fun initView() {
        binding?.apply {
            btnBack.setOnClickListener {
                this@ExchangeActivity.finish()
            }
            btnResult.setOnClickListener {
                hashResultClick()
            }
            btnSwap.setOnClickListener {
                handleSwap()
            }
        }
    }

    override fun initData() {
        mPresenter = PresenterFactory(this).createExchangeActivityPresenter(this)
        mPresenter?.getCoinsData()
    }

    override fun getCoinsDataSuccessfully(coins: MutableList<Coin>) {
        coins.apply {
            coinList = this
            coinSymbolList.clear()
            map {
                coinSymbolList.add(it.symbol)
            }
        }
        binding?.apply {
            progressBar.visibility = View.GONE
            mainView.visibility = View.VISIBLE
            spinnerFrom.apply {
                adapter = SpinnerFactory.createSpinnerAdapterFromArray(
                    this@ExchangeActivity,
                    coinSymbolList
                )
                onItemSelectedListener = SpinnerFactory.createSpinnerListener(
                    {
                        positionFrom = it
                    },
                    {}
                )
            }
            spinnerTo.apply {
                adapter = SpinnerFactory.createSpinnerAdapterFromArray(
                    this@ExchangeActivity,
                    coinSymbolList
                )
                onItemSelectedListener = SpinnerFactory.createSpinnerListener(
                    {
                        positionTo = it
                    },
                    {}
                )
            }
        }
    }

    override fun getCoinsDataFailure(exception: Exception?) {
        this.showToast(exception?.message)
    }

    private fun hashResultClick() {
        binding?.apply {
            inputValue.text.toString().toDoubleOrNull()?.let { value ->
                calculateResult(value)?.let { result ->
                    txtResult.apply {
                        text = getResultString(value, result)
                        visibility = View.VISIBLE
                        setTextColorFromResource(R.color.color_white)
                    }
                    lineChart.apply {
                        createCompareChart(coinList[positionFrom], coinList[positionTo])
                        visibility = View.VISIBLE
                    }
                }
            } ?: run {
                txtResult.apply {
                    text = EXCEPTION_CANNOT_CALCULATE
                    visibility = View.VISIBLE
                    setTextColorFromResource(R.color.color_red)
                }
                lineChart.visibility = View.GONE
            }
        }
    }

    private fun calculateResult(value: Double): Double? {
        coinList[positionFrom].price.toDoubleOrNull()?.let { from ->
            coinList[positionTo].price.toDoubleOrNull()?.let { to ->
                return (from * value) / to
            }
        }
        return null
    }

    private fun getResultString(value: Double, result: Double) = Html.fromHtml(
        """$value <font color=${coinList[positionFrom].color}>${coinList[positionFrom].symbol}</font> 
            = 
            $result <font color=${coinList[positionTo].color}>${coinList[positionTo].symbol}</font>""",
        Html.FROM_HTML_MODE_LEGACY
    )

    private fun handleSwap() {
        binding?.apply {
            val temp = positionFrom
            spinnerFrom.setSelection(positionTo)
            spinnerTo.setSelection(temp)
        }
    }
}
