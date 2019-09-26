/*
 * Created by Muhammad Utsman on 9/26/19 10:46 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/26/19 10:28 AM
 */

package com.utsman.recycling.javabuilder;

// new RecyclingBuilder<String>()
//                .setLayout(R.layout.simple_item_view)
//                .setRecyclerView(recyclerView)
//                .build(new Function4<Recycling<String>, RecyclingAdapter<String>, Context, List<? extends String>, Boolean>() {
//                    @Override
//                    public Boolean invoke(Recycling<String> recycling, RecyclingAdapter<String> adapter, Context context, List<? extends String> strings) {
//                        return null;
//                    }
//                });

// public interface Function4<in P1, in P2, in P3, in P4, out R> : Function<R> {
//    /** Invokes the function with the specified arguments. */
//    public operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4): R
//}

import android.content.Context;

import com.utsman.recycling.adapter.RecyclingAdapter;
import com.utsman.recycling.extentions.Recycling;

import java.util.List;

public interface Builder {
    void invoke(Recycling recycling, RecyclingAdapter recyclingAdapter, Context context, List list);
}
