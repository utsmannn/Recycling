/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.paged.extentions

data class LoaderIdentifierId(val layoutRes: Int? = null,
                              val idLoader: Int? = null,
                              val idTextError: Int? = null) {

    data class Builder(private var loaderRes: Int? = null,
                       private var idLoader: Int? = null,
                       private var idTextError: Int? = null) {

        fun setLoaderRes(id: Int) = apply { this.loaderRes = id }
        fun setIdProgressLoader(id: Int) = apply { this.idLoader = id }
        fun setIdTextViewError(id: Int) = apply { this.idTextError = id }

        fun build() = LoaderIdentifierId(loaderRes, idLoader, idTextError)
    }
}