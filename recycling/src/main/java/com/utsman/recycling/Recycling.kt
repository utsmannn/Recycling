/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.utsman.recycling.adapter.RecyclingAdapter
import com.utsman.recycling.extentions.LoaderIdentifierId
import com.utsman.recycling.extentions.Setup

fun <T> RecyclerView.setupAdapter(layout: Int, identifierId: LoaderIdentifierId? = null, setup: Setup<T>.(adapter: RecyclingAdapter<T>, context: Context, list: List<T>) -> Unit) {
    val setupAdapter = Setup<T>(layout, this, identifierId)

    setup(setupAdapter, setupAdapter.adapter, setupAdapter.context, setupAdapter.getList())
}