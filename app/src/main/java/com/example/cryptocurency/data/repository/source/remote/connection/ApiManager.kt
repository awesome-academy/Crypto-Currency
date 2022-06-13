package com.example.cryptocurency.data.repository.source.remote.connection

object ApiManager {
    private const val BASE_URL = "https://api.coinranking.com/v2"
    private const val COINS_PATH = "/coins"
    private const val DETAIL_COIN_PATH = "/coin/"
    private const val SEARCH_PATH = "/search-suggestions"
    private const val PARAM_REQUEST = "?"
    private const val PARAM_SEPARATOR = "&"
    private const val ORDER_BY_PARAM = "orderBy="
    private const val SEARCH_PARAM = "search="
    private const val LIMIT_PARAM = "limit="
    private const val IDS_PARAM = "uuids[]="

    fun getCoinsUrl(): String =
        BASE_URL + COINS_PATH

    fun getCoinsByIdUrl(coinsId: MutableList<String>): String {
        var result = BASE_URL + COINS_PATH + PARAM_REQUEST
        for (i in 0 until coinsId.size){
            result += IDS_PARAM + coinsId[i]+ PARAM_SEPARATOR
        }
        result +=IDS_PARAM + coinsId.last()
        return result
    }

    fun getCoinsWithScopeUrl(limit: Int, orderBy: String): String =
        BASE_URL + COINS_PATH + PARAM_REQUEST + LIMIT_PARAM + limit.toString() + PARAM_SEPARATOR + ORDER_BY_PARAM + orderBy

    fun getCoinDetailUrl(id: String): String =
        BASE_URL + DETAIL_COIN_PATH + id

    fun getSearchUrl(query: String): String =
        BASE_URL + COINS_PATH + PARAM_REQUEST + SEARCH_PARAM + query

    fun getSearchWithLimitUrl(query: String, limit: Int): String =
        BASE_URL + COINS_PATH + PARAM_REQUEST + SEARCH_PARAM + query + PARAM_SEPARATOR + LIMIT_PARAM + limit.toString()
}
