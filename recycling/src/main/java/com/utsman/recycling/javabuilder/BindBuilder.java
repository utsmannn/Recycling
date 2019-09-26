/*
 * Created by Muhammad Utsman on 9/26/19 10:47 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/26/19 10:47 AM
 */

package com.utsman.recycling.javabuilder;

import android.view.View;

abstract public interface BindBuilder<T> {
    abstract void invoke(View view, T item, Integer position);
}
