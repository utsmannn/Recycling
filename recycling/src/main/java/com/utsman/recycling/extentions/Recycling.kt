/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.extentions

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.View
import com.utsman.recycling.adapter.RecyclingAdapter
import com.utsman.recycling.listener.EndlessScrollListener

@Suppress("UNCHECKED_CAST")
class Recycling<T>(layout: Int, val recyclerView: RecyclerView) {
    internal val adapter = RecyclingAdapter<T>(layout)
    internal val context: Context = recyclerView.context

    fun getList(): List<T?>? = adapter.getCurrentList()

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager)  {
        recyclerView.layoutManager = layoutManager
    }

    fun submitList(list: List<T?>?) {
        Log.i("utsman", "start submit list")
        adapter.addList(list)
    }

    fun submitNetworkState(networkState: NetworkState?) {
        adapter.submitNetwork(networkState)
    }

    fun fixGridSpan(column: Int) {
        (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup = adapter.setGridSpan(column)
    }

    fun submitItem(item: T?) {
        adapter.addItem(item)
    }

    fun onPagingListener(layoutManager: LinearLayoutManager, onPaging: EndlessScrollListener.(page: Int, itemCount: Int) -> Unit) {
        recyclerView.addOnScrollListener(object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                onPaging(this, page, totalItemsCount)
            }
        })
    }

    fun onPagingListener(layoutManager: GridLayoutManager, onPaging: EndlessScrollListener.(page: Int, itemCount: Int) -> Unit) {
        recyclerView.addOnScrollListener(object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                onPaging(this, page, totalItemsCount)
            }
        })
    }

    fun onPagingListener(layoutManager: StaggeredGridLayoutManager, onPaging: EndlessScrollListener.(page: Int, itemCount: Int) -> Unit) {
        recyclerView.addOnScrollListener(object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                onPaging(this, page, totalItemsCount)
            }
        })
    }

    fun bind(bind: Binding<T>.(itemView: View, position: Int, item: T?) -> Unit) {
        adapter.setBinding(bind as Binding<*>.(view: View, position: Int, item: Any?) -> Unit)
    }

    fun addLoader(layoutRes: Int, loader: LoaderIdentifierId.() -> Unit) {
        val loaderIdentifierId = LoaderIdentifierId(layoutRes = layoutRes)
        loader(loaderIdentifierId)
        adapter.addIdentifierId(loaderIdentifierId)
    }

    init {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}