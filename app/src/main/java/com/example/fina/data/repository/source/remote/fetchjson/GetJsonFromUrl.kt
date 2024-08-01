package com.example.fina.data.repository.source.remote.fetchjson

import android.os.Handler
import android.os.Looper
import com.example.fina.data.repository.OnResultListener
import com.example.fina.utils.Constant
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class GetJsonFromUrl<T> constructor(
    private val urlString: String,
    private val keyEntity: String,
    private val listener: OnResultListener<T>,
) {
    private val mExecutor: Executor = Executors.newSingleThreadExecutor()
    private val mHandler: Handler = Handler(Looper.getMainLooper())
    private var data: T? = null

    init {
        callAPI()
    }

    private fun callAPI() {
        mExecutor.execute {
            val responseJson =
                getJsonStringFromUrl(urlString)
            data =
                ParseDataWithJson.parseJsonToData(JSONObject(responseJson), keyEntity) as? T
            mHandler.post {
                data?.let { listener.onSuccess(it) }
            }
        }
    }

    private fun getJsonStringFromUrl(urlString: String): String {
        val url = URL(urlString)
        val httpURLConnection = url.openConnection() as? HttpURLConnection
        httpURLConnection?.setRequestProperty("x-access-token", Constant.API_KEY)
        httpURLConnection?.run {
            connectTimeout = TIME_OUT
            readTimeout = TIME_OUT
            requestMethod = METHOD_GET
            doOutput = true
            connect()
        }

        val bufferedReader = BufferedReader(InputStreamReader(url.openStream()))
        val stringBuilder = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
        bufferedReader.close()
        httpURLConnection?.disconnect()
        return stringBuilder.toString()
    }

    companion object {
        private const val TIME_OUT = 15000
        private const val METHOD_GET = "GET"
    }
}
