/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.paged

import android.content.Context
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.utsman.recycling.paged.adapter.RecyclingAdapter
import com.utsman.recycling.paged.extentions.Recycling

fun <T> RecyclerView.setupAdapterPaged(layoutRes: Int, recycling: Recycling<T>.(adapter: RecyclingAdapter<T>, context: Context, list: PagedList<T>?) -> Unit) {
    val setupAdapter = Recycling<T>(layoutRes, this)
    recycling(setupAdapter, setupAdapter.adapter, setupAdapter.context, setupAdapter.getList())
}

class RecyclingBuilder<T>(private var layout: Int? = null, private var recyclerView: RecyclerView? = null) {
    fun setLayout(layout: Int) = apply { this.layout = layout }
    fun setRecyclerView(recyclerView: RecyclerView) = apply { this.recyclerView = recyclerView }

    fun build(recycling: (Recycling<T>, RecyclingAdapter<T>, Context, PagedList<T>?) -> Boolean): RecyclingBuilder<*>? {
        val setupAdapter = Recycling<T>(layout!!, recyclerView!!)
        recycling(setupAdapter, setupAdapter.adapter, setupAdapter.context,
            setupAdapter.getList()
        )
        return this
    }
}