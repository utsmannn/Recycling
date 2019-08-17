/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 1:56 AM
 */

package com.utsman.recycling.core

data class Pexel(val id: Long,
                 val src: Source
)

data class Source(val small: String)

data class Responses(val page: Int,
                     val per_page: Int,
                     val photos: List<Pexel>)

const val HEADER = "Authorization: 563492ad6f91700001000001880a2e3eb5d6452a94a3dd050f6395a6"
const val BASE_URL = "https://api.pexels.com/"