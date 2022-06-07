package com.example.cryptocurency.data.repository.source.remote.connection

import android.os.Handler
import android.os.Looper
import com.example.cryptocurency.BuildConfig
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.source.OnResultListener
import com.example.cryptocurency.utils.EXCEPTION_NO_DATA
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

object HttpConnection {
    private val TIME_OUT = 15000
    private val METHOD_GET = "GET"
    private val ACCESS_TOKEN = BuildConfig.API_KEY
    private val ACCESS_TOKEN_FIELD = "x-access-token"


    fun callApiCoins(urlString: String, listener: OnResultListener<MutableList<Coin>>) {
        Executors.newSingleThreadExecutor().execute {
            try {
                val responseJson = getJsonFromUrl(urlString)
                val data = JsonHelper.getCoinsDataFromJson(responseJson)
                Handler(Looper.getMainLooper()).post {
                    data?.let {
                        listener.onSuccess(it)
                    } ?: run {
                        listener.onError(Exception(EXCEPTION_NO_DATA))
                    }
                }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post { listener.onError(e) }
            }
        }
    }

    fun callApiDetailCoin(urlString: String, listener: OnResultListener<Coin>) {
        Executors.newSingleThreadExecutor().execute {
            try {
                val responseJson = getJsonFromUrl(urlString)
                val data = JsonHelper.getCoinDetailFromJson(responseJson)
                Handler(Looper.getMainLooper()).post {
                    data?.let {
                        listener.onSuccess(it)
                    } ?: run {
                        listener.onError(Exception(EXCEPTION_NO_DATA))
                    }
                }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post { listener.onError(e) }
            }
        }
    }

    private fun getJsonFromUrl(urlString: String): String {
        val url = URL(urlString)
        val httpURLConnection = url.openConnection() as? HttpURLConnection
        val stringBuilder = StringBuilder()
        try {
            httpURLConnection?.run {
                connectTimeout = TIME_OUT
                readTimeout = TIME_OUT
                requestMethod = METHOD_GET
                doOutput = true
                setRequestProperty(ACCESS_TOKEN_FIELD, ACCESS_TOKEN)
                connect()
            }
            val bufferedReader = BufferedReader(InputStreamReader(url.openStream()))
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            bufferedReader.close()
        } catch (ex: IOException) {
            ex.printStackTrace()
        } finally {
            httpURLConnection?.disconnect()
        }
        return stringBuilder.toString()
    }
}
