/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.utsman.recycling.adapter.RecyclingAdapter
import com.utsman.recycling.extentions.Recycling
import com.utsman.recycling.javabuilder.Builder

fun <T> RecyclerView.setupAdapter(layout: Int, recycling: Recycling<T>.(adapter: RecyclingAdapter<T>, context: Context, list: List<T?>?) -> Unit) {
    val setupAdapter = Recycling<T>(layout, this)
    recycling(setupAdapter, setupAdapter.adapter, setupAdapter.context, setupAdapter.getList())
}

@Suppress("UNCHECKED_CAST")
class RecyclingBuilder<T>(private var layout: Int? = null, private var recyclerView: RecyclerView? = null) {
    fun setLayout(layout: Int) = apply { this.layout = layout }
    fun setRecyclerView(recyclerView: RecyclerView) = apply { this.recyclerView = recyclerView }

    fun build(recycling: (Recycling<T>, RecyclingAdapter<T>, Context, MutableList<out T>) -> Boolean): RecyclingBuilder<*>? {
        val setupAdapter = Recycling<T>(layout!!, recyclerView!!)
        recycling(setupAdapter, setupAdapter.adapter, setupAdapter.context,
            setupAdapter.getList() as MutableList<out T>
        )
        return this
    }

    fun building(recycling: Builder): RecyclingBuilder<T> {
        val setupAdapter = Recycling<T>(layout!!, recyclerView!!)
        recycling(setupAdapter, setupAdapter.adapter, setupAdapter.context,
            setupAdapter.getList() as MutableList<out T>
        )

        return this.building(recycling)
    }
}