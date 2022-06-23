package com.example.cryptocurency.ui.detail

import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.CoinRepository
import com.example.cryptocurency.data.repository.source.OnResultListener
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test

class DetailActivityPresenterTest {

    @RelaxedMockK
    private lateinit var view: DetailActivityContract.View

    @MockK
    private lateinit var coinRepository: CoinRepository
    private lateinit var presenter: DetailActivityContract.Presenter
    private val sampleId = "Qwsogvtv82FCd"

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        presenter = spyk(DetailActivityPresenter(view, coinRepository))
    }

    @Test
    fun `getCoinDetail callback return onSuccess`() {
        val callback = slot<OnResultListener<Coin>>()
        val coin = Coin(id = sampleId)
        val data = mockk<Coin>()
        every {
            coinRepository.getCoinDetail(coin.id, capture(callback))
        } answers {
            callback.captured.onSuccess(data)
        }
        presenter.getCoinDetail(coin)
        verify {
            view.getCoinDetailSuccessfully(data)
        }
    }

    @Test
    fun `getCoinDetail callback return onError`() {
        val callback = slot<OnResultListener<Coin>>()
        val coin = Coin(id = sampleId)
        val exception = mockk<Exception>()
        every {
            coinRepository.getCoinDetail(coin.id, capture(callback))
        } answers {
            callback.captured.onError(exception)
        }
        presenter.getCoinDetail(coin)
        verify {
            view.getCoinDetailFailure(exception)
        }
    }

    @Test
    fun `checkFavorite callback return onSuccess`() {
        val callback = slot<OnResultListener<Boolean>>()
        val coin = Coin(id = sampleId)
        val data = true
        every {
            coinRepository.isFavorite(coin.id, capture(callback))
        } answers {
            callback.captured.onSuccess(data)
        }
        presenter.checkFavorite(coin)
        verify {
            view.checkFavoriteSuccessfully(data)
        }
    }

    @Test
    fun `checkFavorite callback return onError`() {
        val callback = slot<OnResultListener<Boolean>>()
        val coin = Coin(id = sampleId)
        val exception = mockk<Exception>()
        every {
            coinRepository.isFavorite(coin.id, capture(callback))
        } answers {
            callback.captured.onError(exception)
        }
        presenter.checkFavorite(coin)
        verify {
            view.checkFavoriteFailure(exception)
        }
    }

    @Test
    fun `handleFavoriteClick removeCoinFromFavorite onSuccess`() {
        val callback = slot<OnResultListener<Unit>>()
        val coin = Coin(id = sampleId)
        val isFavorite = true
        val data = mockk<Unit>()
        every {
            coinRepository.deleteCoin(coin.id, capture(callback))
        } answers {
            callback.captured.onSuccess(data)
        }
        presenter.handleFavoriteClick(coin, isFavorite)
        verify {
            view.updateFavoriteSuccessfully(!isFavorite)
        }
    }

    @Test
    fun `handleFavoriteClick removeCoinFromFavorite onFailure`() {
        val callback = slot<OnResultListener<Unit>>()
        val coin = Coin(id = sampleId)
        val isFavorite = true
        val exception = mockk<Exception>()
        every {
            coinRepository.deleteCoin(coin.id, capture(callback))
        } answers {
            callback.captured.onError(exception)
        }
        presenter.handleFavoriteClick(coin, isFavorite)
        verify {
            view.updateFavoriteFailure(exception)
        }
    }

    @Test
    fun `handleFavoriteClick insertCoinToFavorite onSuccess`() {
        val callback = slot<OnResultListener<Unit>>()
        val coin = Coin(id = sampleId)
        val isFavorite = false
        val data = mockk<Unit>()
        every {
            coinRepository.insertCoin(coin, capture(callback))
        } answers {
            callback.captured.onSuccess(data)
        }
        presenter.handleFavoriteClick(coin, isFavorite)
        verify {
            view.updateFavoriteSuccessfully(!isFavorite)
        }
    }

    @Test
    fun `handleFavoriteClick insertCoinToFavorite onFailure`() {
        val callback = slot<OnResultListener<Unit>>()
        val coin = Coin(id = sampleId)
        val isFavorite = false
        val exception = mockk<Exception>()
        every {
            coinRepository.insertCoin(coin, capture(callback))
        } answers {
            callback.captured.onError(exception)
        }
        presenter.handleFavoriteClick(coin, isFavorite)
        verify {
            view.updateFavoriteFailure(exception)
        }
    }
}
