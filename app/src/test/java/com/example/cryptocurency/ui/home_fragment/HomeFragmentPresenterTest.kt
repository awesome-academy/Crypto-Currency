package com.example.cryptocurency.ui.home_fragment

import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.CoinRepository
import com.example.cryptocurency.data.repository.source.OnResultListener
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test

class HomeFragmentPresenterTest {
    @RelaxedMockK
    private lateinit var view: HomeFragmentContract.View

    @MockK
    private lateinit var coinRepository: CoinRepository
    private lateinit var presenter: HomeFragmentContract.Presenter
    private val sampleLimit = 50
    private val sampleOrderBy = "marketCap"

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        presenter = spyk(HomeFragmentPresenter(view, coinRepository))
    }

    @Test
    fun `getData callback return onSuccess`() {
        val callback = slot<OnResultListener<MutableList<Coin>>>()
        val data = mockk<MutableList<Coin>>()
        every {
            coinRepository.getRemoteCoinsByScope(sampleLimit, sampleOrderBy, capture(callback))
        } answers {
            callback.captured.onSuccess(data)
        }
        presenter.getCoin(sampleLimit, sampleOrderBy)
        verify {
            view.displayLoading()
            view.hideLoading()
            view.getCoinSuccessfully(data)
        }
    }

    @Test
    fun `getData callback return onFailure`() {
        val callback = slot<OnResultListener<MutableList<Coin>>>()
        val exception = mockk<Exception>()
        every {
            coinRepository.getRemoteCoinsByScope(sampleLimit, sampleOrderBy, capture(callback))
        } answers {
            callback.captured.onError(exception)
        }
        presenter.getCoin(sampleLimit, sampleOrderBy)
        verify {
            view.displayLoading()
            view.hideLoading()
            view.getCoinFailed(exception)
        }
    }
}