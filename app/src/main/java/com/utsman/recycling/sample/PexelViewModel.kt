/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utsman.recycling.extentions.NetworkState
import com.utsman.recycling.core.Pexel
import com.utsman.recycling.core.Responses
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PexelViewModel : ViewModel() {

    private val instance = RetrofitInstance.create()
    private val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    fun getCuratedPhoto(perPage: Int, page: Int): LiveData<List<Pexel>?> {
        val newList: MutableLiveData<List<Pexel>?> = MutableLiveData()
        networkState.postValue(NetworkState.LOADING)
        instance.getCuratedPhoto(perPage, page)
            .enqueue(object : Callback<Responses> {
                override fun onFailure(call: Call<Responses>, t: Throwable) {
                    networkState.postValue(NetworkState.error("error network: ${t.message}"))
                }

                override fun onResponse(call: Call<Responses>, response: Response<Responses>) {
                    val listResponse = response.body()?.photos
                    newList.postValue(listResponse)
                    networkState.postValue(NetworkState.LOADED)
                }

            })

        return newList
    }

    fun getNetworkState(): LiveData<NetworkState> = networkState

}