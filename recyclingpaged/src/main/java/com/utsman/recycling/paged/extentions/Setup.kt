/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.paged.extentions

import android.content.Context
import android.util.Log
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utsman.recycling.paged.adapter.RecyclingAdapter

@Suppress("UNCHECKED_CAST")
class Setup<T>(val layoutRes: Int, val recyclerView: RecyclerView, val identifierId: LoaderIdentifierId?) {

    var adapter = RecyclingAdapter<T>(layoutRes, identifierId)
    val context: Context = recyclerView.context

    fun getList(): PagedList<T>? = adapter.currentList

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager)  {
        recyclerView.layoutManager = layoutManager
    }

    fun setDivider(orientation: Int) {
        recyclerView.addItemDecoration(DividerItemDecoration(context, orientation))
    }

    fun submitList(list: PagedList<T>?) {
        Log.i("anjay", "taiii")
        adapter.submitList(list)
    }

    fun submitNetwork(networkState: NetworkState?) {
        adapter.submitNetworkState(networkState)
    }

    fun fixGridSpan(column: Int) {
        (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup = adapter.setGridSpan(column)
    }

    fun bind(bind: Binding<T>.() -> Unit) {
        adapter.setBinding(bind as Binding<*>.() -> Unit)
    }

    init {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}