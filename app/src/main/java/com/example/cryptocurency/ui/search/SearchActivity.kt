package com.example.cryptocurency.ui.search

import android.content.Intent
import android.database.Cursor
import android.database.MatrixCursor
import android.provider.BaseColumns
import android.view.View
import android.widget.CursorAdapter
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.databinding.ActivitySearchBinding
import com.example.cryptocurency.ui.adapter.ItemClickListener
import com.example.cryptocurency.ui.adapter.SearchCoinAdapter
import com.example.cryptocurency.ui.detail.DetailActivity
import com.example.cryptocurency.utils.COIN_EXTRA
import com.example.cryptocurency.utils.EXCEPTION_NO_DATA
import com.example.cryptocurency.utils.base.BaseActivity
import com.example.cryptocurency.utils.extension.showToast
import com.example.cryptocurency.utils.factory.PresenterFactory

class SearchActivity : BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate),
    SearchActivityContract.View, SearchView.OnQueryTextListener, SearchView.OnSuggestionListener,
    ItemClickListener<Coin> {

    private var mPresenter: SearchActivityContract.Presenter? = null
    private var suggestions = mutableListOf<String>()
    private val coinName = "coinName"
    private var mSuggestionAdapter: SimpleCursorAdapter? = null
    private val searchAdapter = SearchCoinAdapter(this)

    override fun initView() {
        binding?.apply {
            btnBack.setOnClickListener {
                this@SearchActivity.finish()
            }
            searchView.apply {
                isIconified = false
                clearFocus()
                requestFocusFromTouch()
                setOnQueryTextListener(this@SearchActivity)
            }
            recyclerviewSearch.adapter = searchAdapter
        }
    }

    override fun initData() {
        mPresenter = PresenterFactory(this).createSearchActivityPresenter(this).apply {
            getCoinsData()
        }
        mSuggestionAdapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_1,
            null,
            arrayOf(coinName),
            intArrayOf(android.R.id.text1),
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        ).apply {
            binding?.searchView?.let {
                it.suggestionsAdapter = this
                it.setOnSuggestionListener(this@SearchActivity)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            binding?.apply {
                progressBar.visibility = View.VISIBLE
                recyclerviewSearch.visibility = View.GONE
                txtError.visibility = View.GONE
            }
            mPresenter?.searchCoins(it)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            val cursor = MatrixCursor(arrayOf(BaseColumns._ID, coinName))
            suggestions.forEachIndexed { index, suggestion ->
                if (suggestion.contains(it, true)) {
                    cursor.addRow(arrayOf(index, suggestion))
                }
            }
            mSuggestionAdapter?.changeCursor(cursor)
        }
        return true
    }

    override fun onSuggestionSelect(position: Int): Boolean {
        return false
    }

    override fun onSuggestionClick(position: Int): Boolean {
        binding?.searchView?.apply {
            val cursor = suggestionsAdapter.getItem(position) as Cursor
            val columnName = cursor.getColumnIndex(coinName)
            val selection = cursor.getString(columnName)
            setQuery(selection, true)
        }
        return true
    }

    override fun getCoinsDataSuccessfully(coins: MutableList<Coin>) {
        suggestions.clear()
        coins.map {
            suggestions.add(it.name)
        }
    }

    override fun getCoinsDataFailure(exception: Exception?) {
        this.showToast(exception?.message)
    }

    override fun searchCoinsSuccessfully(coins: MutableList<Coin>) {
        binding?.apply {
            progressBar.visibility = View.GONE
            if (coins.isEmpty()) {
                recyclerviewSearch.visibility = View.GONE
                txtError.apply {
                    visibility = View.VISIBLE
                    text = EXCEPTION_NO_DATA
                }
            } else {
                recyclerviewSearch.visibility = View.VISIBLE
                txtError.visibility = View.GONE
                searchAdapter.setAdapterData(coins)
            }
        }
    }

    override fun searchCoinsFailure(exception: Exception?) {
        binding?.apply {
            progressBar.visibility = View.GONE
            recyclerviewSearch.visibility = View.GONE
            txtError.apply {
                visibility = View.VISIBLE
                text = exception?.message
            }
        }
    }

    override fun onItemClick(item: Coin?) {
        item?.let {
            Intent(this, DetailActivity::class.java).apply {
                putExtra(COIN_EXTRA, item)
                startActivity(this)
            }
        }
    }

    override fun onItemLongClick(item: Coin?): Boolean {
        return true
    }
}
