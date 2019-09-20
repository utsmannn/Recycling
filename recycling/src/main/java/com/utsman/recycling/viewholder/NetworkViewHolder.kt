/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.viewholder

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utsman.recycling.extentions.NetworkState
import com.utsman.recycling.extentions.Status

class NetworkViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun bind(idProgress: Int?, idText: Int?, networkState: NetworkState?) = itemView.run {

        val progressLoader = idProgress?.let { findViewById<View>(it) }
        val textView = idText?.let { findViewById<TextView>(it) }

        progressLoader?.visibility = toVisibility(networkState?.status == Status.RUNNING)
        textView?.visibility = toVisibility(networkState?.status == Status.FAILED)

        textView?.text = networkState?.msg
    }

    private fun toVisibility(constraint: Boolean): Int {
        return if (constraint) View.VISIBLE
        else View.GONE
    }
}