/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utsman.recycling.adapter.TYPE.ITEM
import com.utsman.recycling.adapter.TYPE.LOADER
import com.utsman.recycling.extentions.Binding
import com.utsman.recycling.extentions.LoaderIdentifierId
import com.utsman.recycling.extentions.NetworkState
import com.utsman.recycling.viewholder.BaseViewHolder
import com.utsman.recycling.viewholder.NetworkViewHolder

class RecyclingAdapter<T>(private val layoutRes: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var item: T? = null
    private lateinit var setup: Binding<*>.(view: View, position: Int, item: Any?) -> Unit
    private var networkState: NetworkState? = null
    private val list: MutableList<T?> = mutableListOf()
    private var diffResult: DiffUtil.DiffResult? = null
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
                item = list[position]
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
            null -> list.size
            else -> list.size + if (hasExtraRow()) 1 else 0
        }
    }

    internal fun submitNetwork(newNetworkState: NetworkState?) {
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

    internal fun addItem(item: T?) {
        list.add(item)
        notifyDataSetChanged()
    }

    private fun getItem(): T? = item

    internal fun addList(list: List<T>?) {
        Log.i("utsman", "submitting list.. ${list?.size}")
        if (list != null) {
            val diffUtil = ItemDiffUtil(this.list, list)
            diffResult = DiffUtil.calculateDiff(diffUtil)

            this.list.addAll(list)
            diffResult!!.dispatchUpdatesTo(this)
        }
        notifyDataSetChanged()
    }

    internal fun getCurrentList(): List<T?> = list

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