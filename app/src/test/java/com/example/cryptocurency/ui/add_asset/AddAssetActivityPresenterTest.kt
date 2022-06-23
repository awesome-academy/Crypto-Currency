package com.example.cryptocurency.ui.add_asset

import com.example.cryptocurency.data.models.Asset
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.AssetRepository
import com.example.cryptocurency.data.repository.CoinRepository
import com.example.cryptocurency.data.repository.source.OnResultListener
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test

class AddAssetActivityPresenterTest {

    @RelaxedMockK
    private lateinit var view: AddAssetActivityContract.View

    @MockK
    private lateinit var coinRepository: CoinRepository

    @MockK
    private lateinit var assetRepository: AssetRepository
    private lateinit var presenter: AddAssetActivityContract.Presenter

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        presenter = spyk(AddAssetActivityPresenter(view, coinRepository, assetRepository))
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
    fun `addAsset callback return onSuccess`() {
        val callback = slot<OnResultListener<Unit>>()
        val asset = mockk<Asset>()
        val data = mockk<Unit>()
        every {
            assetRepository.insertAsset(asset, capture(callback))
        } answers {
            callback.captured.onSuccess(data)
        }
        presenter.addAsset(asset)
        verify {
            view.addAssetSuccessfully()
        }
    }

    @Test
    fun `addAsset callback return onError`() {
        val callback = slot<OnResultListener<Unit>>()
        val asset = mockk<Asset>()
        val exception = mockk<Exception>()
        every {
            assetRepository.insertAsset(asset, capture(callback))
        } answers {
            callback.captured.onError(exception)
        }
        presenter.addAsset(asset)
        verify {
            view.addAssetFailure(exception)
        }
    }
}
