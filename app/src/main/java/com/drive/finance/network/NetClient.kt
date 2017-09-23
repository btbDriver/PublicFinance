package com.drive.finance.network

import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import rx.Observable
import rx.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by aaron on 2017/9/22.
 */
class NetClient {

    val okHttpClient: OkHttpClient by lazy {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()
    }

    val httpClient: Observable<OkHttpClient> = Observable
            .create<OkHttpClient>({ subscribe ->
                subscribe.onNext(okHttpClient)
                subscribe.onCompleted()
            })
            .subscribeOn(Schedulers.io())

    fun doGetRequest(url: String, paramsMap: Map<String, String>): Observable<JSONObject> {
        return Observable.create<JSONObject> { subscribe ->
            okHttpClient.newCall(parserGetParams(url, paramsMap))
                    .enqueue(object : Callback {
                        override fun onFailure(call: Call?, e: IOException?) {
                            subscribe.onError(e)
                        }

                        override fun onResponse(call: Call?, response: Response?) {
                            if (response != null) {
                                subscribe.onNext(JSONObject(response.body().string()))
                                subscribe.onCompleted()
                            } else {
                                subscribe.onError(NullPointerException())
                            }
                        }
                    })
        }
    }

    fun doGetRequestArray(url: String, paramsMap: Map<String, String>): Observable<JSONArray> {
        return Observable.create<JSONArray> { subscribe ->
            okHttpClient.newCall(parserGetParams(url, paramsMap))
                    .enqueue(object : Callback {
                        override fun onFailure(call: Call?, e: IOException?) {
                            subscribe.onError(e)
                        }

                        override fun onResponse(call: Call?, response: Response?) {
                            if (response != null) {
                                subscribe.onNext(JSONArray(response.body().string()))
                                subscribe.onCompleted()
                            } else {
                                subscribe.onError(NullPointerException())
                            }
                        }
                    })
        }
    }

    fun parserGetParams(url: String, paramsMap: Map<String, String>): Request {
        val it = paramsMap.entries.iterator()
        val sb = StringBuffer(url)
        while(it.hasNext()) {
            val entry = it.next()
            sb.append("&").append(entry.key).append("=").append(entry.value)
        }

        return Request.Builder().get().url(sb.toString()).build()
    }
}