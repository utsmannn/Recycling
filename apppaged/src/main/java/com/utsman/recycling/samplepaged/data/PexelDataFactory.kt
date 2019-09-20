/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:39 AM
 */

package com.utsman.recycling.samplepaged.data

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.utsman.recycling.core.Pexel

class PexelDataFactory : DataSource.Factory<Long, Pexel>() {

    val pagingLiveData = MutableLiveData<PexelDataSource>()
    override fun create(): DataSource<Long, Pexel> {
        val data = PexelDataSource()
        pagingLiveData.postValue(data)
        return data
    }
}