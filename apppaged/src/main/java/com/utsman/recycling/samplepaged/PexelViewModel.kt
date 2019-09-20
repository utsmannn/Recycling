/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.samplepaged

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.utsman.recycling.core.Pexel
import com.utsman.recycling.paged.extentions.NetworkState
import com.utsman.recycling.samplepaged.data.PexelDataFactory
import com.utsman.recycling.samplepaged.data.PexelDataSource

class PexelViewModel : ViewModel() {

    private var pagingDataFactory: PexelDataFactory? = null

    private fun configPaged(size: Int): PagedList.Config = PagedList.Config.Builder()
        .setPageSize(size)
        .setInitialLoadSizeHint(size * 2)
        .setEnablePlaceholders(true)
        .build()

    fun getCuratedPhoto(): LiveData<PagedList<Pexel>> {
        pagingDataFactory = PexelDataFactory()
        return LivePagedListBuilder(pagingDataFactory!!, configPaged(4)).build()
    }

    fun getLoader(): LiveData<NetworkState> = Transformations.switchMap<PexelDataSource, NetworkState>(
        pagingDataFactory?.pagingLiveData!!
    ) { it.networkState }
}