package com.example.cryptocurency.ui.favorite_fragment

import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.CoinRepository
import com.example.cryptocurency.data.repository.source.OnResultListener
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test

class FavoriteFragmentPresenterTest {

    @RelaxedMockK
    private lateinit var view: FavoriteFragmentContract.View

    @MockK
    private lateinit var coinRepository: CoinRepository
    private lateinit var presenter: FavoriteFragmentContract.Presenter
    private val sampleId = "Qwsogvtv82FCd"

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        presenter = spyk(FavoriteFragmentPresenter(view, coinRepository))
    }

    @Test
    fun `getFavoriteList callback return onSuccess`() {
        val callback = slot<OnResultListener<MutableList<Coin>>>()
        val data = mockk<MutableList<Coin>>()
        every {
            coinRepository.getLocalCoins(capture(callback))
        } answers {
            callback.captured.onSuccess(data)
        }
        presenter.getFavoriteList()
        verify {
            view.getFavoriteSuccessfully(data)
        }
    }

    @Test
    fun `getFavoriteList callback return onError`() {
        val callback = slot<OnResultListener<MutableList<Coin>>>()
        val exception = mockk<Exception>()
        every {
            coinRepository.getLocalCoins(capture(callback))
        } answers {
            callback.captured.onError(exception)
        }
        presenter.getFavoriteList()
        verify {
            view.getFavoriteFailure(exception)
        }
    }

    @Test
    fun `updateFavoriteListData onSuccess updateLocalCoin onSuccess`() {
        val coins = mutableListOf<Coin>(Coin(id = sampleId))
        val coinIds = mutableListOf<String>(sampleId)
        val callbackGetData = slot<OnResultListener<MutableList<Coin>>>()
        val callbackLocalUpdate = slot<OnResultListener<Unit>>()
        val dataFromRemote = mockk<MutableList<Coin>>()
        val localUpdateState = mockk<Unit>()
        every {
            coinRepository.updateListCoins(coinIds, capture(callbackGetData))
        } answers {
            callbackGetData.captured.onSuccess(dataFromRemote)
        }
        every {
            coinRepository.updateLocalFavoriteCoins(dataFromRemote, capture(callbackLocalUpdate))
        } answers {
            callbackLocalUpdate.captured.onSuccess(localUpdateState)
        }
        presenter.updateFavoriteListData(coins)
        verify {
            view.updateFavoriteSuccessfully(dataFromRemote)
        }
    }

    @Test
    fun `updateFavoriteListData onSuccess updateLocalCoin onError`() {
        val coins = mutableListOf<Coin>(Coin(id = sampleId))
        val coinIds = mutableListOf<String>(sampleId)
        val callbackGetData = slot<OnResultListener<MutableList<Coin>>>()
        val callbackLocalUpdate = slot<OnResultListener<Unit>>()
        val dataFromRemote = mockk<MutableList<Coin>>()
        val localUpdateState = mockk<Exception>()
        every {
            coinRepository.updateListCoins(coinIds, capture(callbackGetData))
        } answers {
            callbackGetData.captured.onSuccess(dataFromRemote)
        }
        every {
            coinRepository.updateLocalFavoriteCoins(dataFromRemote, capture(callbackLocalUpdate))
        } answers {
            callbackLocalUpdate.captured.onError(localUpdateState)
        }
        presenter.updateFavoriteListData(coins)
        verify {
            view.updateFavoriteSuccessfully(dataFromRemote)
            view.updateFavoriteFailure(localUpdateState)
        }
    }

    @Test
    fun `updateFavoriteListData onError`() {
        val coins = mutableListOf<Coin>(Coin(id = sampleId))
        val coinIds = mutableListOf<String>(sampleId)
        val callbackGetData = slot<OnResultListener<MutableList<Coin>>>()
        val exception = mockk<Exception>()
        every {
            coinRepository.updateListCoins(coinIds, capture(callbackGetData))
        } answers {
            callbackGetData.captured.onError(exception)
        }
        presenter.updateFavoriteListData(coins)
        verify {
            view.updateFavoriteFailure(exception)
        }
    }

    @Test
    fun `removeCoinFromFavorite callback onSuccess`() {
        val callback = slot<OnResultListener<Unit>>()
        val coin = Coin(id = sampleId)
        val data = mockk<Unit>()
        every {
            coinRepository.deleteCoin(coin.id, capture(callback))
        } answers {
            callback.captured.onSuccess(data)
        }
        presenter.removeFavoriteCoin(coin)
        verify {
            view.removeFavoriteCoinSuccessfully(coin)
        }
    }

    @Test
    fun `removeCoinFromFavorite callback onFailure`() {
        val callback = slot<OnResultListener<Unit>>()
        val coin = Coin(id = sampleId)
        val exception = mockk<Exception>()
        every {
            coinRepository.deleteCoin(coin.id, capture(callback))
        } answers {
            callback.captured.onError(exception)
        }
        presenter.removeFavoriteCoin(coin)
        verify {
            view.removeFavoriteCoinFailure(exception)
        }
    }
}
