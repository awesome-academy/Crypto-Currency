package com.example.cryptocurency.ui.search

import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.CoinRepository
import com.example.cryptocurency.data.repository.source.OnResultListener
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test

class SearchActivityPresenterTest {

    @RelaxedMockK
    private lateinit var view: SearchActivityContract.View

    @MockK
    private lateinit var coinRepository: CoinRepository
    private lateinit var presenter: SearchActivityContract.Presenter
    private val sampleQuery = "BTC"

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        presenter = spyk(SearchActivityPresenter(view, coinRepository))
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

    @Test
    fun `searchCoins callback return onSuccess`() {
        val callback = slot<OnResultListener<MutableList<Coin>>>()
        val data = mockk<MutableList<Coin>>()
        every {
            coinRepository.searchCoin(sampleQuery, capture(callback))
        } answers {
            callback.captured.onSuccess(data)
        }
        presenter.searchCoins(sampleQuery)
        verify {
            view.searchCoinsSuccessfully(data)
        }
    }

    @Test
    fun `searchCoins callback return onFailure`() {
        val callback = slot<OnResultListener<MutableList<Coin>>>()
        val exception = mockk<Exception>()
        every {
            coinRepository.searchCoin(sampleQuery, capture(callback))
        } answers {
            callback.captured.onError(exception)
        }
        presenter.searchCoins(sampleQuery)
        verify {
            view.searchCoinsFailure(exception)
        }
    }
}
