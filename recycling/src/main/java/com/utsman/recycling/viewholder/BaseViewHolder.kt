/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.utsman.recycling.extentions.Binding
import com.utsman.recycling.javabuilder.BindBuilder

@Suppress("UNCHECKED_CAST")
class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun <T>bind(setup: Binding<*>.(view: View, position: Int, item: T) -> Unit, item: Any?, position: Int) = itemView.run {
        //val bind = Binding(this, item, position)
        val bind = Binding(this, item, position)
        setup(bind, bind.itemView, bind.position, bind.item as T)
    }
}