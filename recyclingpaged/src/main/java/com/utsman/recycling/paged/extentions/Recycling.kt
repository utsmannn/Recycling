/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.paged.extentions

import android.arch.paging.PagedList
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.utsman.recycling.paged.adapter.RecyclingAdapter

@Suppress("UNCHECKED_CAST")
class Recycling<T>(layoutRes: Int, val recyclerView: RecyclerView) {

    internal var adapter = RecyclingAdapter<T>(layoutRes)
    internal val context: Context = recyclerView.context

    fun getList(): PagedList<T>? = adapter.currentList

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager)  {
        recyclerView.layoutManager = layoutManager
    }

    fun submitList(list: PagedList<T>?) {
        adapter.submitList(list)
    }

    fun submitNetwork(networkState: NetworkState?) {
        adapter.submitNetworkState(networkState)
    }

    fun fixGridSpan(column: Int) {
        (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup = adapter.setGridSpan(column)
    }

    fun addLoader(layoutRes: Int, loader: LoaderIdentifierId.() -> Unit) {
        val loaderIdentifierId = LoaderIdentifierId(layoutRes = layoutRes)
        loader(loaderIdentifierId)
        adapter.addIdentifierId(loaderIdentifierId)
    }

    fun bind(bind: Binding<T>.(itemView: View, position: Int, item: T?) -> Unit) {
        adapter.setBinding(bind as Binding<*>.(view: View, position: Int, item: Any?) -> Unit)
    }

    init {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}