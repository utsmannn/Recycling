/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.paged.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.utsman.recycling.paged.adapter.TYPE.ITEM
import com.utsman.recycling.paged.adapter.TYPE.LOADER
import com.utsman.recycling.paged.extentions.Binding
import com.utsman.recycling.paged.extentions.LoaderIdentifierId
import com.utsman.recycling.paged.extentions.NetworkState
import com.utsman.recycling.paged.viewholder.BaseViewHolder
import com.utsman.recycling.paged.viewholder.NetworkViewHolder

class RecyclingAdapter<T>(private val layoutRes: Int) : PagedListAdapter<T, RecyclerView.ViewHolder>(
    BaseDiffUtil()
) {

    private var item: T? = null
    private lateinit var setup: Binding<*>.(view: View, position: Int, item: Any?) -> Unit
    private var networkState: NetworkState? = null
    private var loaderIdentifierId: LoaderIdentifierId? = null

    internal fun addIdentifierId(loaderIdentifierId: LoaderIdentifierId) {
        this.loaderIdentifierId = loaderIdentifierId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType) {
            ITEM -> BaseViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    layoutRes,
                    parent,
                    false
                )
            )
            LOADER -> NetworkViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    loaderIdentifierId?.layoutRes ?: 0,
                    parent,
                    false
                )
            )
            else  -> throw IllegalArgumentException("not found view holder")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            ITEM -> {
                val itemHolder = holder as BaseViewHolder
                item = getItem(position)

                if (item != null) {
                    try {
                        itemHolder.bind(setup, getItem(), position)
                    } catch (e: UninitializedPropertyAccessException) {
                        throw IllegalArgumentException("don't forget to 'bind' your view")
                    }
                }
            }

            LOADER -> {
                val idLoader = loaderIdentifierId?.idLoader
                val idTextView = loaderIdentifierId?.idTextError
                val netHolder = holder as NetworkViewHolder
                netHolder.bind(idLoader, idTextView, networkState)
            }
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount -1 && loaderIdentifierId != null ) {
            LOADER
        } else {
            ITEM
        }
    }

    override fun getItemCount(): Int {
        return when(loaderIdentifierId) {
            null -> super.getItemCount()
            else -> super.getItemCount() + if (hasExtraRow()) 1 else 0
        }
    }

    internal fun submitNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    internal fun setBinding(binding: Binding<*>.(view: View, position: Int, item: Any?) -> Unit) {
        this.setup = binding
    }

    private fun getItem(): T? = item

    internal fun setGridSpan(column: Int) = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when (getItemViewType(position)) {
                ITEM -> 1
                LOADER -> column
                else -> 1
            }
        }
    }

}


object TYPE {
    const val ITEM = 0
    const val LOADER = 1
}