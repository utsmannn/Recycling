/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.utsman.recycling.extentions.Binding

class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(setup: Binding<*>.() -> Unit, item: Any?, position: Int) = itemView.run {
        setup(Binding(this, item, position))
    }
}