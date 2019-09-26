/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.extentions

import android.view.View
import com.utsman.recycling.javabuilder.BindBuilder

class Binding<T>(internal var itemView: View, internal var item: T?, internal var position: Int)
