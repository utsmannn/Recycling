/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.extentions

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utsman.recycling.adapter.RecyclingAdapter

@Suppress("UNCHECKED_CAST")
class Setup<T>(layout: Int, val recyclerView: RecyclerView, identifierId: LoaderIdentifierId?) {
    val adapter = RecyclingAdapter<T>(layout, identifierId)
    val context: Context = recyclerView.context

    fun getList(): List<T> = adapter.getCurrentList()

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager)  {
        recyclerView.layoutManager = layoutManager
    }

    fun setDivider(orientation: Int) {
        recyclerView.addItemDecoration(DividerItemDecoration(context, orientation))
    }

    fun submitList(list: List<T>?) {
        Log.i("utsman", "start submit list")
        adapter.addList(list)
    }

    fun submitNetworkState(networkState: NetworkState?) {
        adapter.submitNetwork(networkState)
    }

    fun fixGridSpan(column: Int) {
        (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup = adapter.setGridSpan(column)
    }

    fun bind(bind: Binding<T>.() -> Unit) {
        adapter.setBinding(bind as Binding<*>.() -> Unit)
    }

    //fun getLayoutManager(): RecyclerView.LayoutManager = recyclerView.layoutManager!!

    init {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }
}