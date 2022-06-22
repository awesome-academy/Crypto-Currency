package com.example.cryptocurency.ui.exchange

import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.CoinRepository
import com.example.cryptocurency.data.repository.source.OnResultListener
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test

class ExchangeActivityPresenterTest {

    @RelaxedMockK
    private lateinit var view: ExchangeActivityContract.View

    @MockK
    private lateinit var coinRepository: CoinRepository
    private lateinit var presenter: ExchangeActivityContract.Presenter

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        presenter = spyk(ExchangeActivityPresenter(view, coinRepository))
    }

    @Test
    fun `getCoinsData callback return onSuccess`() {
        val callback = slot<OnResultListener<MutableList<Coin>>>()
        val data = mockk<MutableList<Coin>>()
        every {
            coinRepository.getAllRemoteCoins(capture(callback))
        } answers {
            callback.captured.onSuccess(data)
        }
        presenter.getCoinsData()
        verify {
            view.getCoinsDataSuccessfully(data)
        }
    }

    @Test
    fun `getCoinsData callback return onError`() {
        val callback = slot<OnResultListener<MutableList<Coin>>>()
        val exception = mockk<Exception>()
        every {
            coinRepository.getAllRemoteCoins(capture(callback))
        } answers {
            callback.captured.onError(exception)
        }
        presenter.getCoinsData()
        verify {
            view.getCoinsDataFailure(exception)
        }
    }
}
