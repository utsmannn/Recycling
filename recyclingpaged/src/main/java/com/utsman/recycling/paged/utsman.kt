/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.paged

import android.arch.paging.PagedList
import android.content.Context
import android.support.v7.widget.RecyclerView
import com.utsman.recycling.paged.adapter.RecyclingAdapter
import com.utsman.recycling.paged.extentions.Recycling

fun <T> RecyclerView.setupAdapterPaged(layoutRes: Int, recycling: Recycling<T>.(adapter: RecyclingAdapter<T>, context: Context, list: PagedList<T>?) -> Unit) {
    val setupAdapter = Recycling<T>(layoutRes, this)
    recycling(setupAdapter, setupAdapter.adapter, setupAdapter.context, setupAdapter.getList())
}