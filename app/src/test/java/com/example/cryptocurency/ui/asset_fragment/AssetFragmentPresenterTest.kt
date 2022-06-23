package com.example.cryptocurency.ui.asset_fragment

import com.example.cryptocurency.data.models.Asset
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.AssetRepository
import com.example.cryptocurency.data.repository.source.OnResultListener
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test

class AssetFragmentPresenterTest {

    @RelaxedMockK
    private lateinit var view: AssetFragmentContract.View

    @MockK
    private lateinit var assetRepository: AssetRepository
    private lateinit var presenter: AssetFragmentContract.Presenter
    private val sampleAssetId = 1

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        presenter = spyk(AssetFragmentPresenter(view, assetRepository))
    }

    @Test
    fun `getLocalAssets callback return onSuccess`() {
        val callback = slot<OnResultListener<MutableList<Asset>>>()
        val data = mockk<MutableList<Asset>>()
        every {
            assetRepository.getAllAsset(capture(callback))
        } answers {
            callback.captured.onSuccess(data)
        }
        presenter.getLocalAssets()
        verify {
            view.getLocalAssetsSuccessfully(data)
        }
    }

    @Test
    fun `getLocalAssets callback return onError`() {
        val callback = slot<OnResultListener<MutableList<Asset>>>()
        val data = mockk<Exception>()
        every {
            assetRepository.getAllAsset(capture(callback))
        } answers {
            callback.captured.onError(data)
        }
        presenter.getLocalAssets()

        verify {
            view.getLocalAssetsFailure(data)
        }
    }

    @Test
    fun `getAssetsCoinData coinIds empty`() {
        val coinIds = mockk<MutableList<String>>()
        val data = mutableListOf<Coin>()
        every {
            coinIds.isEmpty()
        } answers {
            true
        }
        presenter.getAssetsCoinData(coinIds)
        verify {
            view.getCoinsDataSuccessfully(data)
        }
    }

    @Test
    fun `getAssetsCoinData coinIds not empty callback return onSuccess`() {
        val callback = slot<OnResultListener<MutableList<Coin>>>()
        val coinIds = mockk<MutableList<String>>()
        val data = mutableListOf<Coin>()
        every {
            coinIds.isEmpty()
        } answers {
            false
        }
        every {
            assetRepository.getAssetCoinData(coinIds, capture(callback))
        } answers {
            callback.captured.onSuccess(data)
        }
        presenter.getAssetsCoinData(coinIds)
        verify {
            view.getCoinsDataSuccessfully(data)
        }
    }

    @Test
    fun `getAssetsCoinData coinIds not empty callback return onFailure`() {
        val callback = slot<OnResultListener<MutableList<Coin>>>()
        val coinIds = mockk<MutableList<String>>()
        val exception = mockk<Exception>()
        every {
            coinIds.isEmpty()
        } answers {
            false
        }
        every {
            assetRepository.getAssetCoinData(coinIds, capture(callback))
        } answers {
            callback.captured.onError(exception)
        }
        presenter.getAssetsCoinData(coinIds)
        verify {
            view.getLocalAssetsFailure(exception)
        }
    }

    @Test
    fun `removeAsset callback return onSuccess`() {
        val callback = slot<OnResultListener<Unit>>()
        val asset = Asset(id = sampleAssetId)
        val data = mockk<Unit>()
        every {
            assetRepository.deleteAsset(asset.id, capture(callback))
        } answers {
            callback.captured.onSuccess(data)
        }
        presenter.removeAsset(asset)
        verify {
            view.removeAssetSuccessfully(asset)
        }
    }

    @Test
    fun `removeAsset callback return onFailure`() {
        val callback = slot<OnResultListener<Unit>>()
        val asset = Asset(id = sampleAssetId)
        val exception = mockk<Exception>()
        every {
            assetRepository.deleteAsset(asset.id, capture(callback))
        } answers {
            callback.captured.onError(exception)
        }
        presenter.removeAsset(asset)
        verify {
            view.removeAssetFailure(exception)
        }
    }
}
