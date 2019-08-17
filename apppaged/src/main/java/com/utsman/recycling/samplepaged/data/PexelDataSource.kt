/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.samplepaged.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.utsman.recycling.core.Pexel
import com.utsman.recycling.core.Responses
import com.utsman.recycling.core.loge
import com.utsman.recycling.core.logi
import com.utsman.recycling.paged.extentions.NetworkState
import com.utsman.recycling.samplepaged.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class PexelDataSource : ItemKeyedDataSource<Long, Pexel>() {

    private val perPage = 10
    private var page = 1
    var networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Pexel>) {
        networkState.postValue(NetworkState.LOADING)
        RetrofitInstance.create().getCuratedPhoto(perPage, page)
            .enqueue(object : Callback<Responses> {
                override fun onResponse(call: Call<Responses>, response: Response<Responses>) {
                    val body = response.body()
                    if (body != null) {
                        page++
                        logi("eeeeee -- ${body.photos}")
                        networkState.postValue(NetworkState.LOADED)
                        logi("aaaaaa -> ${body.photos[0]}")
                        callback.onResult(body.photos)
                    }
                }

                override fun onFailure(call: Call<Responses>, t: Throwable) {
                    //Log.e("anjay", t.localizedMessage)
                    loge(t.localizedMessage)
                    networkState.postValue(NetworkState.error(t.localizedMessage))
                }

            })
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Pexel>) {
        networkState.postValue(NetworkState.LOADING)
        RetrofitInstance.create().getCuratedPhoto(perPage, page)
            .enqueue(object : Callback<Responses> {
                override fun onResponse(call: Call<Responses>, response: Response<Responses>) {
                    val body = response.body()
                    if (body != null) {
                        page++
                        networkState.postValue(NetworkState.LOADING)
                        callback.onResult(body.photos)
                    }
                }

                override fun onFailure(call: Call<Responses>, t: Throwable) {
                    //Log.e("anjay", t.localizedMessage)
                    loge(t.localizedMessage)
                    networkState.postValue(NetworkState.error(t.localizedMessage))
                }

            })
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Pexel>) {}

    override fun getKey(item: Pexel): Long = item.id
}