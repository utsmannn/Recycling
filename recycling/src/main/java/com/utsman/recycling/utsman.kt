/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.utsman.recycling.adapter.RecyclingAdapter
import com.utsman.recycling.extentions.Recycling

fun <T> RecyclerView.setupAdapter(layout: Int, recycling: Recycling<T>.(adapter: RecyclingAdapter<T>, context: Context, list: List<T?>?) -> Unit) {
    val setupAdapter = Recycling<T>(layout, this)

    recycling(setupAdapter, setupAdapter.adapter, setupAdapter.context, setupAdapter.getList())
}