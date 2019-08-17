/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.paged

import androidx.recyclerview.widget.RecyclerView
import com.utsman.recycling.paged.extentions.LoaderIdentifierId
import com.utsman.recycling.paged.extentions.Setup

fun <T> RecyclerView.setupAdapterPaged(layoutRes: Int, loaderIdentifierId: LoaderIdentifierId? = null, setup: Setup<T>.() -> Unit) {
    setup(Setup(layoutRes, this, loaderIdentifierId))
}