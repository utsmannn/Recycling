/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.paged.extentions

import android.content.Context
import android.util.Log
import android.view.View
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utsman.recycling.paged.adapter.RecyclingAdapter

@Suppress("UNCHECKED_CAST")
class Setup<T>(layoutRes: Int, val recyclerView: RecyclerView, identifierId: LoaderIdentifierId?) {

    internal var adapter = RecyclingAdapter<T>(layoutRes, identifierId)
    internal val context: Context = recyclerView.context

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

    fun bind(bind: Binding<T>.(itemView: View, position: Int, item: T?) -> Unit) {
        adapter.setBinding(bind as Binding<*>.(view: View, position: Int, item: Any?) -> Unit)
    }

    init {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}